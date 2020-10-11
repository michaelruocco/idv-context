package uk.co.idv.identity.entities.channel.fake;

public interface FakeChannelMother{

    static FakeChannel build() {
        return new FakeChannel();
    }

}
