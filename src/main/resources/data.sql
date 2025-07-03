-- Ensure the 'student' table exists
CREATE TABLE IF NOT EXISTS student
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    address         VARCHAR(255)        NOT NULL,
    date_of_birth   DATE                NOT NULL,
    registered_date DATE                NOT NULL
);

-- Insert well-known UUIDs for specific students (South African names and addresses, born 1990-2005)
INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '123e4567-e89b-12d3-a456-426614174000',
       'Sipho Dlamini',
       'sipho.dlamini@example.com',
       '12 Vilakazi St, Soweto, Johannesburg',
       '1992-04-15',
       '2024-01-10'
WHERE NOT EXISTS (SELECT 1
                  FROM student
                  WHERE id = '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       'Thandiwe Mokoena',
       'thandiwe.mokoena@example.com',
       '101 Florida Rd, Durban',
       '1998-09-23',
       '2023-12-01'
WHERE NOT EXISTS (SELECT 1
                  FROM student
                  WHERE id = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '123e4567-e89b-12d3-a456-426614174002',
       'Lethabo Nkosi',
       'lethabo.nkosi@example.com',
       '45 Long St, Cape Town',
       '2001-03-12',
       '2022-06-20'
WHERE NOT EXISTS (SELECT 1
                  FROM student
                  WHERE id = '123e4567-e89b-12d3-a456-426614174002');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '123e4567-e89b-12d3-a456-426614174003',
       'Ayanda Khumalo',
       'ayanda.khumalo@example.com',
       '88 Steve Biko Ave, Pretoria',
       '1995-11-30',
       '2023-05-14'
WHERE NOT EXISTS (SELECT 1
                  FROM student
                  WHERE id = '123e4567-e89b-12d3-a456-426614174003');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '123e4567-e89b-12d3-a456-426614174004',
       'Zanele Ndlovu',
       'zanele.ndlovu@example.com',
       '23 Chief Albert Luthuli St, Pietermaritzburg',
       '2003-02-05',
       '2024-03-01'
WHERE NOT EXISTS (SELECT 1
                  FROM student
                  WHERE id = '123e4567-e89b-12d3-a456-426614174004');

-- Insert well-known UUIDs for specific students
INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174005',
       'Sibusiso Mthembu',
       'sibusiso.mthembu@example.com',
       '7 Beach Rd, Port Elizabeth',
       '1994-07-25',
       '2024-02-15'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174005');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174006',
       'Naledi Molefe',
       'naledi.molefe@example.com',
       '56 Nelson Mandela Dr, Bloemfontein',
       '1997-04-18',
       '2023-08-25'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174006');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174007',
       'Katlego Phiri',
       'katlego.phiri@example.com',
       '34 Oxford St, East London',
       '2000-01-11',
       '2022-10-10'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174007');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174008',
       'Busisiwe Zulu',
       'busisiwe.zulu@example.com',
       '19 Church St, Polokwane',
       '1999-09-02',
       '2024-04-20'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174008');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174009',
       'Mpho Radebe',
       'mpho.radebe@example.com',
       '77 Voortrekker St, Kimberley',
       '1996-11-15',
       '2023-06-30'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174009');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174010',
       'Tebogo Mahlangu',
       'tebogo.mahlangu@example.com',
       '5 Paul Kruger St, Nelspruit',
       '1993-08-09',
       '2023-01-22'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174010');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174011',
       'Nomvula Sibanda',
       'nomvula.sibanda@example.com',
       '61 Market St, Rustenburg',
       '2002-05-03',
       '2024-05-12'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174011');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174012',
       'Khanyisa Maseko',
       'khanyisa.maseko@example.com',
       '14 Main Rd, George',
       '1991-12-25',
       '2022-11-11'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174012');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174013',
       'Lindiwe Madonsela',
       'lindiwe.madonsela@example.com',
       '28 President St, Mahikeng',
       '1990-06-08',
       '2023-09-19'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174013');

INSERT INTO student (id, name, email, address, date_of_birth, registered_date)
SELECT '223e4567-e89b-12d3-a456-426614174014',
       'Onke Jacobs',
       'onke.jacobs@example.com',
       '90 Govan Mbeki Ave, Port Elizabeth',
       '2004-10-17',
       '2024-03-29'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = '223e4567-e89b-12d3-a456-426614174014');