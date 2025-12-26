INSERT INTO users (id, name, phone, role)
VALUES
    (1, 'Agent One',   '9000000001', 'AGENT'),
    (2, 'Agent Two',   '9000000002', 'AGENT'),
    (3, 'Agent Three', '9000000003', 'AGENT')
    ON DUPLICATE KEY UPDATE
                         name = VALUES(name),
                         role = VALUES(role);


INSERT INTO agent_status (user_id, status)
VALUES
    (1, 'AVAILABLE'),
    (2, 'AVAILABLE'),
    (3, 'AVAILABLE')
    ON DUPLICATE KEY UPDATE
                         status = VALUES(status);



INSERT INTO agent_hierarchy (agent_id, manager_id)
VALUES
    (2, 1),
    (3, 1)
    ON DUPLICATE KEY UPDATE
                         manager_id = VALUES(manager_id);