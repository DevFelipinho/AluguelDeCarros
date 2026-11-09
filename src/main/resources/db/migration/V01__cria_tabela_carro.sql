CREATE TABLE public.carro (
    id bigserial NOT NULL,
    placa text NOT NULL UNIQUE,
    cor text NOT NULL,
    modelo text NOT NULL,
    marca text NOT NULL,
    valor_diaria numeric(10,2) NOT NULL,
    disponivel boolean NOT NULL DEFAULT true,
    status text NOT NULL DEFAULT 'ATIVO',
    PRIMARY KEY (id)
);
