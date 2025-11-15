package com.youssef.GridPulse.domain.base;

import com.youssef.GridPulse.common.base.*;
import com.youssef.GridPulse.utils.TestLogger;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public abstract class BaseServiceTest<
        E extends BaseEntity,
        H extends BaseHistoryEntity,
        ID extends UUID,
        INPUT,
        S extends BaseService<E, H, ID, INPUT>> {

    // Remove @Mock annotations from these
    protected JpaRepository<E, ID> repository;
    protected BaseHistoryRepository<H, ID> historyRepository;
    protected BaseMapper<E, H, INPUT> mapper;

    protected S service;

    // Add constructor to accept mocks
    protected BaseServiceTest(JpaRepository<E, ID> repository,
                              BaseHistoryRepository<H, ID> historyRepository,
                              BaseMapper<E, H, INPUT> mapper) {
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.mapper = mapper;
    }


    private static Instant suiteStartTime;
    private static int testCounter = 1;

    protected abstract S createService();

    protected abstract E createTestEntity(ID id);

    protected abstract H createTestHistoryEntity(ID originalId);

    protected abstract INPUT createTestInput();

    protected abstract ID createTestId();

    @BeforeAll
    static void beginTestExecution() {
        suiteStartTime = Instant.now();
        TestLogger.logSuiteStart(BaseHistoryRepositoryTest.class);
    }

    @AfterAll
    static void endTestExecution() {
        TestLogger.logSuiteEnd(BaseHistoryRepositoryTest.class, suiteStartTime);
    }

    @BeforeEach
    void setUp() {
        service = createService();
        TestLogger.logTestStart(testCounter);
    }

    @AfterEach
    void tearDown() {
        TestLogger.logTestEnd(testCounter);
        testCounter++;
    }

    @Nested
    class CreateTests {
        @Test
        void create_ShouldSaveEntityAndHistory() {
            // Given
            INPUT input = createTestInput();
            E entity = createTestEntity(createTestId());
            H history = createTestHistoryEntity((ID) entity.getId());

            // mapper must return a non-null entity
            when(mapper.toEntity(input)).thenReturn(entity);

            // simulate JPA assigning ID on saveAndFlush
            when(repository.saveAndFlush(any())).thenAnswer(inv -> {
                E e = inv.getArgument(0);
                e.setId(createTestId()); // mimic @GeneratedValue
                return e;
            });


            // stub findById to return the saved entity
            when(repository.findById(any())).thenReturn(Optional.of(entity));
            // mapper.toHistory must use the saved entity
            when(mapper.toHistory(any())).thenReturn(history);

            when(historyRepository.saveAndFlush(history)).thenReturn(history);

            // When
            E result = service.create(input);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(createTestId());
            verify(repository).saveAndFlush(any());
            verify(historyRepository).saveAndFlush(history);
            verify(mapper).toEntity(input);
            verify(mapper).toHistory(result);
        }
    }

    @Nested
    class ReadTests {
        @Test
        void getAll_ShouldReturnAllEntities() {
            // Given
            List<E> entities = List.of(createTestEntity(createTestId()), createTestEntity(createTestId()));
            when(repository.findAll()).thenReturn(entities);

            // When
            List<E> result = service.getAll();

            // Then
            assertThat(result).hasSize(2).containsAll(entities);
            verify(repository).findAll();
        }

        @Test
        void getEntityById_WhenExists_ShouldReturnEntity() {
            // Given
            ID id = createTestId();
            E entity = createTestEntity(id);
            when(repository.findById(id)).thenReturn(Optional.of(entity));

            // When
            E result = service.getEntityById(id);

            // Then
            assertThat(result).isEqualTo(entity);
            verify(repository).findById(id);
        }

        @Test
        void getEntityById_WhenNotExists_ShouldThrowException() {
            // Given
            ID id = createTestId();
            when(repository.findById(id)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> service.getEntityById(id))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Entity not found");
            verify(repository).findById(id);
        }
    }

    @Nested
    class UpdateTests {
        @Test
        void update_WhenExists_ShouldUpdateEntityAndCreateHistory() {
            // Given
            ID id = createTestId();
            INPUT input = createTestInput();
            E existingEntity = createTestEntity(id);
            E updatedEntity = createTestEntity(id);
            H history = createTestHistoryEntity(id);

            when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
            when(mapper.toHistory(existingEntity)).thenReturn(history);
            when(historyRepository.save(history)).thenReturn(history);
            when(repository.save(existingEntity)).thenReturn(updatedEntity);

            // When
            E result = service.update(id, input);

            // Then
            assertThat(result).isEqualTo(updatedEntity);
//            verify(repository).findById(id);
            verify(mapper).toHistory(existingEntity);
            verify(historyRepository).save(history);
            verify(mapper).updateEntity(input, existingEntity);
            verify(repository).save(existingEntity);
        }

        @Test
        void update_WhenNotExists_ShouldThrowException() {
            // Given
            ID id = createTestId();
            INPUT input = createTestInput();
            when(repository.findById(id)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> service.update(id, input))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Entity not found");
            verify(repository).findById(id);
            verifyNoInteractions(mapper, historyRepository);
        }
    }

    @Nested
    class DeleteTests {
        @Test
        void delete_WhenExists_ShouldDeleteEntityAndCreateHistory() {
            // Given
            ID id = createTestId();
            E entity = createTestEntity(id);
            H history = createTestHistoryEntity(id);

            when(repository.findById(id)).thenReturn(Optional.of(entity));
            when(mapper.toHistory(entity)).thenReturn(history);
            when(historyRepository.save(history)).thenReturn(history);

            // When
            boolean result = service.delete(id);

            // Then
            assertThat(result).isTrue();
//            verify(repository).findById(id);
            verify(mapper).toHistory(entity);
            verify(historyRepository).save(history);
            verify(repository).delete(entity);
        }

        @Test
        void delete_WhenNotExists_ShouldThrowException() {
            // Given
            ID id = createTestId();
            when(repository.findById(id)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> service.delete(id))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Entity not found");
            verify(repository).findById(id);
            verifyNoInteractions(mapper, historyRepository);
            verify(repository, never()).delete(any());
        }
    }

    @Nested
    class HistoryTests {
        @Test
        void findAllHistory_ShouldReturnAllHistory() {
            // Given
            List<H> historyList = List.of(
                    createTestHistoryEntity(createTestId()),
                    createTestHistoryEntity(createTestId())
            );
            when(historyRepository.findAll()).thenReturn(historyList);

            // When
            List<H> result = service.findAllHistory();

            // Then
            assertThat(result).hasSize(2).containsAll(historyList);
            verify(historyRepository).findAll();
        }

        @Test
        void findHistoryByOriginalId_ShouldReturnMatchingHistory() {
            // Given
            ID id = createTestId();
            List<H> historyList = List.of(
                    createTestHistoryEntity(id),
                    createTestHistoryEntity(id)
            );
            when(historyRepository.findByOriginalId(id)).thenReturn(historyList);

            // When
            List<H> result = service.findHistoryByOriginalId(id);

            // Then
            assertThat(result).hasSize(2).containsAll(historyList);
            verify(historyRepository).findByOriginalId(id);
        }

        @Test
        void markHistoryRecordAsSynced_WhenExists_ShouldMarkAsSynced() {
            // Given
            ID historyId = createTestId();
            when(historyRepository.markAsSynced(historyId)).thenReturn(1);

            // When
            boolean result = service.markHistoryRecordAsSynced(historyId);

            // Then
            assertThat(result).isTrue();
            verify(historyRepository).markAsSynced(historyId);
        }

        @Test
        void markHistoryRecordAsSynced_WhenNotExists_ShouldThrowException() {
            // Given
            ID historyId = createTestId();
            when(historyRepository.markAsSynced(historyId)).thenReturn(0);

            // When & Then
            assertThatThrownBy(() -> service.markHistoryRecordAsSynced(historyId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("History record not found: " + historyId);
            verify(historyRepository).markAsSynced(historyId);
        }
    }
}