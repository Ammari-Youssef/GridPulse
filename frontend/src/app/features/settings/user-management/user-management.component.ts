import { Component, OnInit } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from '@core/models/classes/User.model';
import { Role } from '@core/models/enums/role.enum';
import { GetAllUsersResponse } from '@core/models/interfaces/get-all-user.response';
import { GET_ALL_USERS } from '@graphql/schema/queries/users/get-all.query';

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

  constructor(private readonly apollo: Apollo, private readonly snackBar: MatSnackBar) {}

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
          console.log('✅ Loaded users:', this.users);
        },
        error: (error) => {
          console.error('❌ Error loading users:', error);
          this.loading = false;
          this.snackBar.open('Failed to load users', 'Close', {
            duration: 3000,
          });
        },
      });
  }

  onAddUser() {
    this.snackBar.open('Add user feature coming soon', 'Close', {
      duration: 3000,
    });
  }

  onEditUser(user: User) {
    this.snackBar.open(`Edit ${user.firstname} ${user.lastname}`, 'Close', {
      duration: 3000,
    });
  }

  onDeleteUser(user: User) {
    if (
      confirm(
        `Are you sure you want to delete ${user.firstname} ${user.lastname}?`
      )
    ) {
      // TODO: Implement delete mutation
      this.snackBar.open(`User deleted (not implemented)`, 'Close', {
        duration: 3000,
      });
    }
  }

  onToggleStatus(user: User) {
    // TODO: Implement toggle mutation
    user.enabled = !user.enabled;
    this.snackBar.open(
      `User ${user.enabled ? 'enabled' : 'disabled'}`,
      'Close',
      { duration: 3000 }
    );
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
