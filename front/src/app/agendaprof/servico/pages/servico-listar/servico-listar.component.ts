import {Component, OnInit} from '@angular/core';
import {ListarServicoDto} from "../../core/dto/listar-servico-dto";
import {MessageService} from "primeng/api";
import {DialogService} from "primeng/dynamicdialog";
import {ServicoIncluirComponent} from "../servico-incluir/servico-incluir.component";

@Component({
    selector: 'app-servico-listar',
    templateUrl: './servico-listar.component.html',
    styleUrl: './servico-listar.component.scss',
    providers: [MessageService, DialogService]
})
export class ServicoListarComponent implements OnInit {

    servicos: ListarServicoDto[];
    selectedServicos: ListarServicoDto[];

    cols: { field: string, header: string }[] = [];

    constructor(private messageService: MessageService, private dialogService: DialogService) {

    }


    ngOnInit(): void {
        this.cols = [
            {field: 'nome', header: 'Nome'},
            {field: 'valor', header: 'Valor'},
        ]
    }

    excluir(servico: ListarServicoDto) {

    }

    editar(servico: ListarServicoDto) {

    }

    listar($event: Event) {

    }

    deleteSelectedServicos() {

    }

    incluir() {
        this.dialogService.open(ServicoIncluirComponent, {
            width: '60vw',
            height: '50vh',
        }).onClose.subscribe(() => {
            this.ngOnInit();
        });
    }
}
