import {NgModule} from '@angular/core';
import {ServicoListarComponent} from "./pages/servico-listar/servico-listar.component";
import {RouterModule} from "@angular/router";
import {ButtonModule} from "primeng/button";
import {CurrencyPipe, NgClass, NgIf} from "@angular/common";
import {FileUploadModule} from "primeng/fileupload";
import {InputTextModule} from "primeng/inputtext";
import {RatingModule} from "primeng/rating";
import {RippleModule} from "primeng/ripple";
import {SharedModule} from "primeng/api";
import {TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {ServicoIncluirComponent} from "./pages/servico-incluir/servico-incluir.component";
import {FormsModule} from "@angular/forms";
import {InputTextareaModule} from "primeng/inputtextarea";
import {InputNumberModule} from "primeng/inputnumber";


@NgModule({
    declarations: [ServicoListarComponent, ServicoIncluirComponent],
    imports: [
        RouterModule.forChild([
            {path: '', component: ServicoListarComponent},
        ]),
        ButtonModule,
        CurrencyPipe,
        FileUploadModule,
        InputTextModule,
        RatingModule,
        RippleModule,
        SharedModule,
        TableModule,
        ToastModule,
        ToolbarModule,
        FormsModule,
        NgIf,
        InputTextareaModule,
        InputNumberModule,
        NgClass,
    ]
})
export class ServicoRoutingModule {
}
