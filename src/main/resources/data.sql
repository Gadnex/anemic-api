-- Insert sample products
insert into
    product (
        product_id,
        version,
        name,
        description
    )
values (
        '3fa85f64-5717-4562-b3fc-2c963f66afa6',
        0,
        'Product 1',
        'Product 1 description'
    );

insert into
    product (
        product_id,
        version,
        name,
        description
    )
values (
        'f0bd1867-9923-4d4d-b95d-c52982d9f2c2',
        0,
        'Product 2',
        'Product 2 description'
    );

insert into
    product (
        product_id,
        version,
        name,
        description
    )
values (
        'c5936473-c270-4948-b3ec-4b01f0fa04e7',
        0,
        'Product 3',
        'Product 3 description'
    );

-- Insert sample backlog items
insert into
    backlog_item (
        backlog_item_id,
        version,
        product_id,
        name,
        summary,
        story,
        story_points,
        type,
        status
    )
values (
        '1fe4fa4a-c693-48ed-bcd1-73da1891fce9',
        0,
        '3fa85f64-5717-4562-b3fc-2c963f66afa6',
        'Backlog Item 1',
        'Backlog Item 1 summary',
        'A story for Backlog Item 1',
        3,
        0,
        0
    );

insert into
    backlog_item (
        backlog_item_id,
        version,
        product_id,
        name,
        summary,
        story,
        story_points,
        type,
        status
    )
values (
        '4070d1d5-1b29-4f83-8efe-2d9110d2ea20',
        0,
        'f0bd1867-9923-4d4d-b95d-c52982d9f2c2',
        'Backlog Item 2',
        'Backlog Item 2 summary',
        'A story for Backlog Item 2',
        3,
        0,
        0
    );

insert into
    backlog_item (
        backlog_item_id,
        version,
        product_id,
        name,
        summary,
        story,
        story_points,
        type,
        status
    )
values (
        'c0617642-b6d0-43a6-abad-222f30fa3867',
        0,
        'c5936473-c270-4948-b3ec-4b01f0fa04e7',
        'Backlog Item 3',
        'Backlog Item 3 summary',
        'A story for Backlog Item 3',
        3,
        0,
        0
    );

-- Insert sample sprints
insert into
    sprint (
        sprint_id,
        version,
        product_id,
        name,
        start_date,
        end_date
    )
values (
        '8f82a93a-de1c-4c3d-b092-079092c6fb07',
        0,
        '3fa85f64-5717-4562-b3fc-2c963f66afa6',
        'Sprint 1',
        '2025-01-01',
        '2025-01-31'
    );

insert into
    sprint (
        sprint_id,
        version,
        product_id,
        name,
        start_date,
        end_date
    )
values (
        '573ba9e5-ec1f-40c6-a503-c68410f68ad0',
        0,
        '3fa85f64-5717-4562-b3fc-2c963f66afa6',
        'Sprint 2',
        '2025-02-01',
        '2025-02-28'
    );

insert into
    sprint (
        sprint_id,
        version,
        product_id,
        name,
        start_date,
        end_date
    )
values (
        '794b0311-916f-434b-b0d8-4c570c20b1be',
        0,
        '3fa85f64-5717-4562-b3fc-2c963f66afa6',
        'Sprint 3',
        '2024-03-01',
        '2024-03-31'
    );