import { Component, OnInit, OnDestroy } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription as RxSubscription } from 'rxjs';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { ISubscription } from '../../models/ISubscription';
import { AddSubscriptionModalComponent } from '../../components/add-subscription-modal/add-subscription-modal.component';

@Component({
  selector: 'app-subscription-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class SubscriptionListComponent implements OnInit, OnDestroy {
  subscriptions: ISubscription[] = [];
  private rxSubscriptions: RxSubscription[] = [];
  isLoading = false;
  isModalOpen = false;


  constructor(
    private subscriptionService: SubscriptionService,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.loadSubscriptions();
  }

  ngOnDestroy() {
    this.rxSubscriptions.forEach(sub => sub.unsubscribe());
  }

  loadSubscriptions() {
    this.isLoading = true;
    const sub = this.subscriptionService.getAllSubscriptions().subscribe({
      next: (data) => {
        this.subscriptions = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Ошибка загрузки:', err);
        this.isLoading = false;
      }
    });
    this.rxSubscriptions.push(sub);
  }

openAddSubscriptionModal() {
  const modalRef = this.modalService.open(AddSubscriptionModalComponent, {
    centered: true,
    backdrop: 'static'
  });

  modalRef.result.then(
    (result) => {
      if (result) {
        this.addSubscription(result);
      }
    },
    () => {
      // Модальное окно закрыто без отправки формы
    }
  );
}

  addSubscription(newSubscription: Omit<ISubscription, 'id'>) {
    this.isLoading = true;
    const sub = this.subscriptionService.createSubscription(newSubscription)
      .subscribe({
        next: (createdSubscription) => {
          this.subscriptions = [...this.subscriptions, createdSubscription];
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Ошибка при создании:', err);
          this.isLoading = false;
        }
      });
    this.rxSubscriptions.push(sub);
  }

  updateSubscription(id: number, changes: Partial<ISubscription>) {
  this.isLoading = true;
  const sub = this.subscriptionService.updateSubscription(id, changes)
    .subscribe({
      next: (updatedSub) => {
        const index = this.subscriptions.findIndex(s => s.id === id);
        if (index !== -1) {
          this.subscriptions[index] = updatedSub;
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Ошибка обновления:', err);
        this.isLoading = false;
        // Можно показать сообщение об ошибке пользователю
      }
    });
  this.rxSubscriptions.push(sub);
}

  deleteSubscription(id: number) {
    if (confirm('Вы уверены, что хотите удалить эту подписку?')) {
      this.isLoading = true;
      const sub = this.subscriptionService.deleteSubscription(id)
        .subscribe({
          next: () => {
            this.subscriptions = this.subscriptions.filter(s => s.id !== id);
            this.isLoading = false;
          },
          error: (err) => {
            console.error('Ошибка удаления:', err);
            this.isLoading = false;
          }
        });
      this.rxSubscriptions.push(sub);
    }
  }
    // Для хранения оригинальных значений при редактировании
  private originalValues = new Map<number, ISubscription>();

  startEditing(sub: ISubscription) {
    // Сохраняем оригинальные значения
    this.originalValues.set(sub.id, {...sub});
    sub.editing = true;
  }

  cancelEditing(sub: ISubscription) {
    // Восстанавливаем оригинальные значения
    const original = this.originalValues.get(sub.id);
    if (original) {
      Object.assign(sub, original);
    }
    sub.editing = false;
    this.originalValues.delete(sub.id);
  }

  saveSubscription(sub: ISubscription) {
    const changes = {
      serviceName: sub.serviceName,
      startDate: sub.startDate,
      endDate: sub.endDate,
      cost: sub.cost,
      reminderEnabled: sub.reminderEnabled
    };

    this.updateSubscription(sub.id, changes);
    sub.editing = false;
    this.originalValues.delete(sub.id);
  }
  handleModalClose(result?: any) {
    this.isModalOpen = false;
    if (result) {
      this.addSubscription(result);
    }
  }
}