package uk.co.idv.context.adapter.json.activity;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.activity.DefaultActivity;
import uk.co.idv.context.entities.activity.Login;
import uk.co.idv.context.entities.activity.OnlinePurchase;
import uk.co.idv.identity.adapter.json.alias.cardnumber.CardNumberModule;

import java.util.Arrays;

public class ActivityModule extends SimpleModule {

    public ActivityModule() {
        super("activity-module", Version.unknownVersion());

        addDeserializer(Activity.class, new ActivityDeserializer());
        addDeserializer(DefaultActivity.class, new DefaultActivityDeserializer());
        addDeserializer(Login.class, new LoginDeserializer());
        addDeserializer(OnlinePurchase.class, new OnlinePurchaseDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new CardNumberModule(),
                new MoneyModule(),
                new JavaTimeModule()
        );
    }

}
