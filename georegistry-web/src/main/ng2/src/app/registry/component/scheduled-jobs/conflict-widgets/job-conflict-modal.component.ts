import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

import { ParentReferenceProblemWidgetComponent } from './parent-reference-problem-widget.component'
import { TermReferenceProblemWidgetComponent } from './term-reference-problem-widget.component'
import { RowValidationProblemWidgetComponent } from './row-validation-problem-widget.component'
import { ErrorHandler } from '../../../../shared/component/error-handler/error-handler';
import { GeoObjectType, MasterList, ScheduledJob, ScheduledJobOverview } from '../../../model/registry';

import { RegistryService } from '../../../service/registry.service';

import { IOService } from '../../../service/io.service';
import { LocalizationService } from '../../../../shared/service/localization.service';

@Component( {
    selector: 'job-conflict-modal',
    templateUrl: './job-conflict-modal.component.html',
    styleUrls: []
} )
export class JobConflictModalComponent implements OnInit {
    message: string = null;
    problem: any;
    job: ScheduledJobOverview;
    
    /*
     * Observable subject for submission.  Called when an update is successful 
     */
    onConflictAction: Subject<any>;

    readonly: boolean = false;
    edit: boolean = false;


    constructor( private service: RegistryService, private iService: IOService, private lService: LocalizationService, public bsModalRef: BsModalRef ) {
      this.onConflictAction = new Subject();
    }

    ngOnInit(): void {
      
    }
    
    onProblemResolvedListener(problem: any): void {
      this.onConflictAction.next({action:"RESOLVED", data: problem});
    }

    onCancel(): void {
        this.bsModalRef.hide()
    }

    error( err: HttpErrorResponse ): void {
            this.message = ErrorHandler.getMessageFromError(err);
    }

}
