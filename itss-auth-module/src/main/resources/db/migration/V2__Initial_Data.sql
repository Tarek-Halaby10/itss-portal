-- V2__Initial_Data.sql
-- Place this file in: itss-auth-module/src/main/resources/db/migration/

-- Insert default permissions
INSERT INTO permissions (name, description, resource, action, endpoint) VALUES
-- User Management Permissions
('USER_VIEW', 'View users list and details', 'users', 'READ', '/api/users/**'),
('USER_CREATE', 'Create new users', 'users', 'CREATE', '/api/users'),
('USER_UPDATE', 'Update user information', 'users', 'UPDATE', '/api/users/**'),
('USER_DELETE', 'Delete users', 'users', 'DELETE', '/api/users/**'),
('USER_APPROVE', 'Approve pending users', 'users', 'APPROVE', '/api/users/*/approve'),
('USER_BLOCK', 'Block/unblock users', 'users', 'BLOCK', '/api/users/*/block'),

-- Role Management Permissions
('ROLE_VIEW', 'View roles list and details', 'roles', 'READ', '/api/roles/**'),
('ROLE_CREATE', 'Create new roles', 'roles', 'CREATE', '/api/roles'),
('ROLE_UPDATE', 'Update role information', 'roles', 'UPDATE', '/api/roles/**'),
('ROLE_DELETE', 'Delete roles', 'roles', 'DELETE', '/api/roles/**'),
('ROLE_ASSIGN', 'Assign roles to users', 'roles', 'ASSIGN', '/api/users/*/roles'),

-- Permission Management Permissions
('PERMISSION_VIEW', 'View permissions list', 'permissions', 'READ', '/api/permissions/**'),
('PERMISSION_ASSIGN', 'Assign permissions to roles', 'permissions', 'ASSIGN', '/api/roles/*/permissions'),

-- Invitation Permissions
('INVITATION_SEND', 'Send user invitations', 'invitations', 'CREATE', '/api/invitations'),
('INVITATION_VIEW', 'View sent invitations', 'invitations', 'READ', '/api/invitations/**'),
('INVITATION_CANCEL', 'Cancel pending invitations', 'invitations', 'DELETE', '/api/invitations/**'),

-- Activity Monitoring Permissions
('ACTIVITY_VIEW', 'View user activity logs', 'activities', 'READ', '/api/activities/**'),

-- Dashboard Permissions
('DASHBOARD_VIEW', 'Access admin dashboard', 'dashboard', 'READ', '/api/dashboard/**');

-- Insert default roles
INSERT INTO roles (name, description, is_template, is_system_role) VALUES
('SUPER_ADMIN', 'Super Administrator with full access', FALSE, TRUE),
('ADMIN', 'Administrator with most permissions', TRUE, FALSE),
('USER_MANAGER', 'Can manage users and basic operations', TRUE, FALSE),
('VIEWER', 'Read-only access to most resources', TRUE, FALSE),
('BASIC_USER', 'Basic user with minimal permissions', TRUE, FALSE);

-- Get role IDs for permission assignments
-- Super Admin gets all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id 
FROM roles r, permissions p 
WHERE r.name = 'SUPER_ADMIN';

-- Admin gets most permissions (except some super admin specific ones)
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id 
FROM roles r, permissions p 
WHERE r.name = 'ADMIN' 
AND p.name IN (
    'USER_VIEW', 'USER_CREATE', 'USER_UPDATE', 'USER_APPROVE', 'USER_BLOCK',
    'ROLE_VIEW', 'ROLE_CREATE', 'ROLE_UPDATE', 'ROLE_ASSIGN',
    'PERMISSION_VIEW', 'PERMISSION_ASSIGN',
    'INVITATION_SEND', 'INVITATION_VIEW', 'INVITATION_CANCEL',
    'ACTIVITY_VIEW', 'DASHBOARD_VIEW'
);

-- User Manager permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id 
FROM roles r, permissions p 
WHERE r.name = 'USER_MANAGER' 
AND p.name IN (
    'USER_VIEW', 'USER_UPDATE', 'USER_APPROVE', 'USER_BLOCK',
    'ROLE_VIEW', 'ROLE_ASSIGN',
    'INVITATION_SEND', 'INVITATION_VIEW',
    'ACTIVITY_VIEW', 'DASHBOARD_VIEW'
);

-- Viewer permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id 
FROM roles r, permissions p 
WHERE r.name = 'VIEWER' 
AND p.name IN (
    'USER_VIEW', 'ROLE_VIEW', 'PERMISSION_VIEW', 
    'INVITATION_VIEW', 'ACTIVITY_VIEW', 'DASHBOARD_VIEW'
);

-- Basic User permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id 
FROM roles r, permissions p 
WHERE r.name = 'BASIC_USER' 
AND p.name IN ('DASHBOARD_VIEW');

-- Create Super Admin user (admin/admin)
INSERT INTO users (username, email, password_hash, first_name, last_name, status) VALUES
('admin', 'admin@itss.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Super', 'Admin', 'APPROVED');
-- Note: The password hash above is for 'admin' - in production, generate a proper hash

-- Assign Super Admin role to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id 
FROM users u, roles r 
WHERE u.username = 'admin' AND r.name = 'SUPER_ADMIN';