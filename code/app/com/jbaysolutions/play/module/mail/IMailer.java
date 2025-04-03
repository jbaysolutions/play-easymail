package com.jbaysolutions.play.module.mail;

import akka.actor.Cancellable;
import com.jbaysolutions.play.module.mail.Mailer.Mail;

public interface IMailer {
	public Cancellable sendMail(final Mail email);

	public Cancellable sendMail(final String subject, final String textBody, final String recipient);

	public Cancellable sendMail(final String subject, final Mail.Body body, final String recipient);
}
