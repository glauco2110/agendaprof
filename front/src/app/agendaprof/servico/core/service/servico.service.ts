import { Injectable } from '@angular/core';
import {ServicoBase} from "../../../core/service/servico-base";
import Servico from "../entity/servico";
import {ServicoBaseProviderService} from "../../../core/service/servico-base.provider.service";

@Injectable({
  providedIn: 'root'
})
export class ServicoService  extends ServicoBase{

  constructor( servicoBaseProvider: ServicoBaseProviderService) {
      super(servicoBaseProvider);
  }

    protected getPathEndPoint(): string {
        return "/servico";
    }
}
