package uk.co.idv.activity.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.activity.entities.DefaultActivity;
import uk.co.idv.activity.entities.Login;
import uk.co.idv.activity.entities.OnlinePurchase;

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
                new MoneyModule(),
                new JavaTimeModule()
        );
    }

}
