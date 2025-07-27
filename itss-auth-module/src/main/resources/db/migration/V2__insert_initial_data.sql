-- Insert initial permissions if they don't exist
INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'VIEW_USERS', 'Permission to view users', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'VIEW_USERS');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'BLOCK_USER', 'Permission to block user', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'BLOCK_USER');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'CREATE_ROLE', 'Permission to create role', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'CREATE_ROLE');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'UPDATE_ROLE', 'Permission to update role', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'UPDATE_ROLE');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'DELETE_ROLE', 'Permission to delete role', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'DELETE_ROLE');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'VIEW_ROLES', 'Permission to view roles', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'VIEW_ROLES');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'ASSIGN_PERMISSIONS', 'Permission to assign permissions', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'ASSIGN_PERMISSIONS');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'VIEW_AUDIT_LOGS', 'Permission to view audit logs', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'VIEW_AUDIT_LOGS'); 