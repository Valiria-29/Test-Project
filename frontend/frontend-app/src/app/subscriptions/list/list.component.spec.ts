import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { SubscriptionService } from '../../services/subscription.service';
import {ISubscription } from '../../models/ISubscription';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-subscription-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  
  @ViewChild('confirmDialog') confirmDialog!: TemplateRef<any>;
  subscriptionToDelete: ISubscription | null = null;

    subscriptions: ISubscription[] = [];

    constructor(
    private subService: SubscriptionService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.loadSubscriptions();
  }

  loadSubscriptions() {
    this.subService.getAllSubscriptions().subscribe({
      next: (data) => this.subscriptions = data,
      error: (err) => console.error('Error loading subscriptions', err)
    });
  }
  confirmDelete(subscription: ISubscription) {
    this.subscriptionToDelete = subscription;
    this.dialog.open(this.confirmDialog);
  }

  onConfirmDelete() {
    if (this.subscriptionToDelete?.id) {
      this.deleteSubscription(this.subscriptionToDelete.id);
    }
    this.dialog.closeAll();
  }

  onCancelDelete() {
    this.subscriptionToDelete = null;
    this.dialog.closeAll();
  }

  deleteSubscription(id: number) {
    this.subService.deleteSubscription(id).subscribe({
      next: () => {
        this.subscriptions = this.subscriptions.filter(s => s.id !== id);
        // Можно добавить уведомление об успешном удалении
      },
      error: (err) => {
        console.error('Ошибка при удалении:', err);
        // Можно добавить уведомление об ошибке
      }
    });
  }
}
