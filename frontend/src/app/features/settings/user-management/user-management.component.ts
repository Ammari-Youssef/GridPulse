import { Component, OnInit } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { User } from '@core/models/classes/User.model';
import { Role } from '@core/models/enums/role.enum';
import { GetAllUsersResponse } from '@core/models/interfaces/get-all-user.response';
import { GET_ALL_USERS } from '@graphql/schema/queries/users/get-all.query';
import { SnackbarService } from '@shared/services/snackbar.service'; 

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrl: './user-management.component.scss',
  standalone: false,
})
export class UserManagementComponent implements OnInit {
  users: User[] = [];
  loading = false;
  displayedColumns: string[] = ['name', 'email', 'role', 'status', 'actions'];

  constructor(
    private readonly apollo: Apollo,
    private readonly snackbar: SnackbarService
  ) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.loading = true;

    this.apollo
      .query<GetAllUsersResponse>({
        query: GET_ALL_USERS,
        fetchPolicy: 'network-only',
      })
      .subscribe({
        next: ({ data }) => {
          this.users = data!.getAllUsers;
          this.loading = false;
        },
        error: () => {
          this.loading = false;
          this.snackbar.showError('Failed to load users');
        },
      });
  }

  onAddUser() {
    this.snackbar.showInfo('Add user feature coming soon');
  }

  onEditUser(user: User) {
    this.snackbar.showInfo(`Edit ${user.firstname} ${user.lastname}`);
  }

  onDeleteUser(user: User) {
    if (
      confirm(
        `Are you sure you want to delete ${user.firstname} ${user.lastname}?`
      )
    ) {
      // TODO: Implement delete mutation
      this.snackbar.showError(`User deleted (not implemented)`);
    }
  }

  onToggleStatus(user: User) {
    // TODO: Implement toggle mutation
    user.enabled = !user.enabled;
    this.snackbar.showInfo(`User ${user.enabled ? 'enabled' : 'disabled'}`);
  }

  getRoleLabel(role: Role): string {
    return role === Role.ADMIN ? 'Administrator' : 'User';
  }

  getRoleColor(role: Role): string {
    return role === Role.ADMIN
      ? 'bg-blue-100 text-blue-700'
      : 'bg-purple-100 text-purple-700';
  }
}
