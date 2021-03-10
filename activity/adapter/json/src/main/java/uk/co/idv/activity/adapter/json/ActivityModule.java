package uk.co.idv.activity.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.activity.adapter.json.onlinepurchase.OnlinePurchaseDeserializer;
import uk.co.idv.activity.adapter.json.onlinepurchase.OnlinePurchaseMixin;
import uk.co.idv.activity.adapter.json.onlinepurchase.PurchaseCardDeserializer;
import uk.co.idv.activity.adapter.json.onlinepurchase.PurchaseCardMixin;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.activity.entities.DefaultActivity;
import uk.co.idv.activity.entities.Login;
import uk.co.idv.activity.entities.onlinepurchase.OnlinePurchase;
import uk.co.idv.activity.entities.onlinepurchase.PurchaseCard;

import java.util.Arrays;

public class ActivityModule extends SimpleModule {

    public ActivityModule() {
        super("activity-module", Version.unknownVersion());

        addDeserializer(Activity.class, new ActivityDeserializer());
        addDeserializer(DefaultActivity.class, new DefaultActivityDeserializer());
        addDeserializer(Login.class, new LoginDeserializer());

        setUpOnlinePurchase();
    }

    private void setUpOnlinePurchase() {
        setMixInAnnotation(PurchaseCard.class, PurchaseCardMixin.class);
        addDeserializer(PurchaseCard.class, new PurchaseCardDeserializer());

        setMixInAnnotation(OnlinePurchase.class, OnlinePurchaseMixin.class);
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
