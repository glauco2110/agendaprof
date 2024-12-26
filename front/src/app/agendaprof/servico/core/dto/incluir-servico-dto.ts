import {DTO} from "../../../core/service/servico-base";

export class IncluirServicoDto implements DTO{
    public nome: string;
    public descricao: string;
    public valor: number;
}
