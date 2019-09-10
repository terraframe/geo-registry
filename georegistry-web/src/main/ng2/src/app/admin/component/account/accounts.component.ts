///
/// Copyright (c) 2015 TerraFrame, Inc. All rights reserved.
///
/// This file is part of Runway SDK(tm).
///
/// Runway SDK(tm) is free software: you can redistribute it and/or modify
/// it under the terms of the GNU Lesser General Public License as
/// published by the Free Software Foundation, either version 3 of the
/// License, or (at your option) any later version.
///
/// Runway SDK(tm) is distributed in the hope that it will be useful, but
/// WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU Lesser General Public License for more details.
///
/// You should have received a copy of the GNU Lesser General Public
/// License along with Runway SDK(tm).  If not, see <http://www.gnu.org/licenses/>.
///

import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { Response } from '@angular/http';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { MessageContainer } from '../../../shared/model/core';
import { ConfirmModalComponent } from '../../../shared/component/modals/confirm-modal.component';
import { LocalizationService } from '../../../shared/service/localization.service';

import { User, PageResult, Account } from '../../model/account';
import { AccountService } from '../../service/account.service';
import { AccountComponent } from './account.component';
import { AccountInviteComponent } from './account-invite.component';

declare let acp: string;

@Component( {
    selector: 'accounts',
    templateUrl: './accounts.component.html',
    styles: ['./accounts.css']
} )
export class AccountsComponent implements OnInit, MessageContainer {
    res: PageResult = {
        resultSet: [],
        count: 0,
        pageNumber: 1,
        pageSize: 10
    };
    bsModalRef: BsModalRef;
    message: string = null;

    constructor(
        private router: Router,
        private service: AccountService,
        private modalService: BsModalService,
        private localizeService: LocalizationService
    ) { }

    ngOnInit(): void {
        this.service.page( 1 ).then( res => {
            this.res = res;
        } ).catch(( err: Response ) => {
            this.service.error( err, this );
        } );
    }

    remove( user: User ): void {
        this.service.remove( user.oid ).then( response => {
            this.res.resultSet = this.res.resultSet.filter( h => h.oid !== user.oid );
        } )
            .catch(( err: Response ) => {
                this.service.error( err, this );
            } );
    }

    onClickRemove( account: User ): void {
        this.bsModalRef = this.modalService.show( ConfirmModalComponent, {
            animated: true,
            backdrop: true,
            ignoreBackdropClick: true,
        } );
        this.bsModalRef.content.message = this.localizeService.decode( "account.removeContent" );
        this.bsModalRef.content.submitText = this.localizeService.decode( "modal.button.delete" );

        this.bsModalRef.content.onConfirm.subscribe( data => {
            this.remove( account );
        } );
    }

    edit( user: User ): void {
        // this.router.navigate(['/admin/account', user.oid]);

        this.bsModalRef = this.modalService.show( AccountComponent, {
            animated: true,
            backdrop: true,
            ignoreBackdropClick: true,
        } );
        this.bsModalRef.content.oid = user.oid;

        let that = this;
        ( <AccountComponent>this.bsModalRef.content ).onEdit.subscribe( data => {
            let updatedUserIndex = that.res.resultSet.map(
                function( e ) { return e.oid; }
            ).indexOf( data.oid );

            if ( updatedUserIndex !== -1 ) {
                that.res.resultSet[updatedUserIndex] = data;
            }
        } );
    }

    newInstance(): void {
        // this.router.navigate(['/admin/account', 'NEW']);

        this.bsModalRef = this.modalService.show( AccountComponent, {
            animated: true,
            backdrop: true,
            ignoreBackdropClick: true,
        } );
        this.bsModalRef.content.oid = 'NEW';

        let that = this;
        this.bsModalRef.content.onEdit.subscribe( data => {
            this.onPageChange( this.res.pageNumber );
        } );

    }

    onPageChange( pageNumber: number ): void {
        this.service.page( pageNumber ).then( res => {
            this.res = res;
        } ).catch(( err: Response ) => {
            this.service.error( err, this );
        } );
    }

    inviteUsers(): void {
        // this.router.navigate(['/admin/invite']);	  

        this.bsModalRef = this.modalService.show( AccountInviteComponent, {
            animated: true,
            backdrop: true,
            ignoreBackdropClick: true,
        } );
    }

    setMessage( message: string ) {
        this.message = message;
    }
}