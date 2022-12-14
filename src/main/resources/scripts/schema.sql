
create table actor
(
    actor_id
        constraint actor_pk
            primary key,
    first_name,
    last_name,
    last_update
);

create table film
(
    film_id
        constraint film_pk
            primary key,
    title,
    description,
    release_year,
    language_id,
    original_language_id,
    rental_duration,
    rental_rate,
    length,
    replacement_cost,
    rating,
    special_features,
    last_update
);

create table film_actor
(
    actor_id
        constraint film_actor_actor__fk
            references actor,
    film_id
        constraint film_actor_film__fk
            references film,
    last_update,
    constraint film_actor_pk
        primary key (actor_id, film_id)
);

