-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'BLOCKED')),
    phone VARCHAR(20),
    last_login_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT
);

-- Roles table
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    is_template BOOLEAN DEFAULT FALSE,
    is_system_role BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT
);

-- Permissions table
CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    resource VARCHAR(50) NOT NULL,
    action VARCHAR(20) NOT NULL,
    endpoint VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User roles mapping
CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    assigned_by BIGINT REFERENCES users(id),
    PRIMARY KEY (user_id, role_id)
);

-- Role permissions mapping
CREATE TABLE role_permissions (
    role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE,
    permission_id BIGINT REFERENCES permissions(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id, permission_id)
);

-- Refresh tokens
CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    expires_at TIMESTAMP NOT NULL,
    is_revoked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Password reset tokens
CREATE TABLE password_reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    expires_at TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Invitation tokens
CREATE TABLE invitation_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(100) NOT NULL,
    role_id BIGINT REFERENCES roles(id),
    expires_at TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    accepted_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id)
);

-- User activity log
CREATE TABLE user_activities (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    action VARCHAR(100) NOT NULL,
    resource VARCHAR(50),
    resource_id BIGINT,
    details TEXT,
    ip_address INET,
    user_agent TEXT,
    success BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_tokens_expires_at ON refresh_tokens(expires_at);
CREATE INDEX idx_user_activities_user_id ON user_activities(user_id);
CREATE INDEX idx_user_activities_created_at ON user_activities(created_at);
CREATE INDEX idx_invitation_tokens_token ON invitation_tokens(token);
CREATE INDEX idx_password_reset_tokens_token ON password_reset_tokens(token);

-- Add foreign key constraints
ALTER TABLE users ADD CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) REFERENCES users(id);
ALTER TABLE users ADD CONSTRAINT fk_users_updated_by FOREIGN KEY (updated_by) REFERENCES users(id);
ALTER TABLE roles ADD CONSTRAINT fk_roles_created_by FOREIGN KEY (created_by) REFERENCES users(id);
ALTER TABLE roles ADD CONSTRAINT fk_roles_updated_by FOREIGN KEY (updated_by) REFERENCES users(id);