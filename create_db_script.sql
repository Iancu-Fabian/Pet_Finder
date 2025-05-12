-- Create the database
CREATE DATABASE IF NOT EXISTS pet_match;
USE pet_match;

-- Create the User table
CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    phone_number VARCHAR(20),
    city VARCHAR(50),
    county VARCHAR(50),
    coordinates POINT,
    profile_photo VARCHAR(255)
);

-- Create the Pet table
CREATE TABLE Pet (
    pet_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    breed VARCHAR(50),
    age INT,
    size VARCHAR(20),
    weight DECIMAL(5,2),
    gender ENUM('Male', 'Female'),
    color VARCHAR(50),
    personality_traits TEXT,
    description TEXT,
    fav_toy VARCHAR(100),
    fav_treat VARCHAR(100),
    fav_activity VARCHAR(100),
    vaccination_status VARCHAR(50),
    owner_id INT,
    img1 VARCHAR(255),
    img2 VARCHAR(255),
    img3 VARCHAR(255),
    FOREIGN KEY (owner_id) REFERENCES User(user_id)
);

-- Create the Swipe table
CREATE TABLE Swipe (
    swipe_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    pet_id INT,
    swipe_direction ENUM('left', 'right'),
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id)
);

-- Insert sample users
INSERT INTO User (firstname, lastname, email, password, phone_number, city, county, coordinates, profile_photo)
VALUES 
('Alice', 'Smith', 'alice@example.com', 'password123', '555-1234', 'New York', 'New York County', ST_GeomFromText('POINT(40.7128 -74.0060)'), 'alice_photo.jpg'),
('Bob', 'Johnson', 'bob@example.com', 'securepass456', '555-5678', 'Los Angeles', 'Los Angeles County', ST_GeomFromText('POINT(34.0522 -118.2437)'), 'bob_photo.jpg');

-- Insert sample pets
INSERT INTO Pet (name, breed, age, size, weight, gender, color, personality_traits, description, fav_toy, fav_treat, fav_activity, vaccination_status, owner_id, img1, img2, img3)
VALUES
('Buddy', 'Golden Retriever', 3, 'Large', 30.50, 'Male', 'Golden', 'Friendly, Energetic', 'Loves to play fetch.', 'Tennis Ball', 'Peanut Butter Biscuits', 'Running in the park', 'Up to date', 1, 'buddy1.jpg', 'buddy2.jpg', 'buddy3.jpg'),
('Luna', 'Siberian Husky', 2, 'Medium', 22.30, 'Female', 'Black and White', 'Playful, Vocal', 'Enjoys the snow and howling.', 'Squeaky Bone', 'Beef Jerky', 'Playing in snow', 'Up to date', 2, 'luna1.jpg', 'luna2.jpg', 'luna3.jpg');

-- Insert sample swipes
INSERT INTO Swipe (user_id, pet_id, swipe_direction)
VALUES
(1, 2, 'right'),
(2, 1, 'left');