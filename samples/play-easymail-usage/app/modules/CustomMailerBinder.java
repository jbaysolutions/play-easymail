package modules;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.jbaysolutions.play.module.mail.IMailer;
import com.jbaysolutions.play.module.mail.Mailer;
import com.jbaysolutions.play.module.mail.Mailer.MailerFactory;

public class CustomMailerBinder extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(IMailer.class, Mailer.class).build(MailerFactory.class));
	}

}
