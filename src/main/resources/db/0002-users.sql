-- liquibase formatted sql
-- changeSet users failOnError:true

INSERT INTO User (FirstName, LastName, LogoColor) VALUES
    ('Homer', 'Simpson', '#faf7af'),
    ('Marge', 'Simpson', '#b167e3'),
    ('Bart', 'Simpson', '#fad8af'),
    ('Lisa', 'Simpson', '#f0c5eb'),
    ('Ned', 'Flanders', '#afe0fa'),
    ('Barney', 'Gumble', '#4002d8'),
    ('Moe', 'Szyslak', '#1aa264'),
    ('Seymour', 'Skinner', '#669bd9'),
    ('Kent', 'Brockman', '#a3b8aa');
