CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    task_status VARCHAR(15) NOT NULL
)