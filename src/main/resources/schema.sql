CREATE TABLE IF NOT EXISTS recipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    cuisine VARCHAR(255),
    rating FLOAT,
    prep_time INT NOT NULL,
    cook_time INT NOT NULL,
    total_time INT NOT NULL,
    description TEXT,
    nutrients JSON,
    serves VARCHAR(255)
);
