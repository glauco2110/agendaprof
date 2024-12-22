import { Component } from '@angular/core';
import {IncluirServicoDto} from "../../core/dto/incluir-servico-dto";
import {DynamicDialogRef} from "primeng/dynamicdialog";
import {ServicoService} from "../../core/service/servico.service";

@Component({
  selector: 'app-servico-incluir',
  templateUrl: './servico-incluir.component.html',
  styleUrl: './servico-incluir.component.scss'
})
export class ServicoIncluirComponent {
    servico: IncluirServicoDto;
    submitted: boolean = false;

    constructor(private ref: DynamicDialogRef,
                private service: ServicoService) {
        this.servico = new IncluirServicoDto();
    }

    salvar() {
        this.submitted = true;
        if(this.servico?.nome?.length > 0 && this.servico?.valor > 0) {
            this.service.salvar(this.servico);
        }
    }

    cancelar(){
        this.ref.destroy();
    }
}
