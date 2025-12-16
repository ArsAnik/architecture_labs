DROP TABLE IF EXISTS bookings;

CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    client_id INTEGER NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    room_number VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('created', 'confirmed', 'cancelled', 'checked_in', 'checked_out')),
    booked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

INSERT INTO bookings (client_id, check_in, check_out, room_number, status, booked_at) VALUES
    (1, '2025-12-10', '2025-12-15', '101', 'created', '2025-12-01 10:30:00'),
    (2, '2025-12-12', '2025-12-18', '102', 'confirmed', '2025-12-02 14:20:00'),
    (3, '2025-12-08', '2025-12-10', '103', 'checked_in', '2025-12-01 09:15:00'),
    (4, '2025-12-20', '2025-12-25', '104', 'confirmed', '2025-12-03 16:45:00'),
    (1, '2025-12-25', '2025-12-30', '104', 'confirmed', '2025-12-04 11:00:00'),
    (5, '2025-12-05', '2025-12-07', '106', 'checked_out', '2025-11-28 13:30:00'),
    (6, '2025-12-15', '2025-12-20', '107', 'created', '2025-12-02 15:20:00'),
    (7, '2025-12-11', '2025-12-13', '105', 'cancelled', '2025-12-01 12:00:00');
