import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-subscription-modal',
  templateUrl: './add-subscription-modal.component.html',
  styleUrls: ['./add-subscription-modal.component.css']
})
export class AddSubscriptionModalComponent {
  subscriptionForm: FormGroup;
  showEmailField = false;

  constructor(
    private fb: FormBuilder,
    public activeModal: NgbActiveModal
  ) {
    this.subscriptionForm = this.fb.group({
      serviceName: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      cost: [0, [Validators.required, Validators.min(0)]],
      reminderEnabled: [false],
      userEmail: ['']
    });
  }

  toggleEmailField() {
    this.showEmailField = this.subscriptionForm.get('reminderEnabled')?.value;
    const emailControl = this.subscriptionForm.get('userEmail');
    
    if (this.showEmailField) {
      emailControl?.setValidators([Validators.required, Validators.email]);
    } else {
      emailControl?.clearValidators();
    }
    emailControl?.updateValueAndValidity();
  }

  onSubmit() {
    if (this.subscriptionForm.valid) {
      this.activeModal.close(this.subscriptionForm.value);
    }
  }

  onCancel() {
    this.activeModal.dismiss();
  }
}