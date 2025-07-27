-- Add new permissions for user management and permissions viewing
INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'INVITE_USER', 'Permission to invite users', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'INVITE_USER');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'APPROVE_USER', 'Permission to approve users', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'APPROVE_USER');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'DECLINE_USER', 'Permission to decline users', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'DECLINE_USER');

INSERT INTO permissions (name, description, created_at, updated_at) 
SELECT 'VIEW_PERMISSIONS', 'Permission to view permissions', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'VIEW_PERMISSIONS'); 