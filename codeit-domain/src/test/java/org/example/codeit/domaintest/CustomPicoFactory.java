package org.example.codeit.domaintest;

import io.cucumber.core.backend.ObjectFactory;
import io.cucumber.picocontainer.PicoFactory;
import org.example.codeit.domain.CodeSubmitter;
import org.example.codeit.domain.profile.ProfileManager;
import org.example.codeit.domain.spi.stubs.*;

public class CustomPicoFactory implements ObjectFactory {

    private PicoFactory delegate = new PicoFactory();

    public CustomPicoFactory() {
        addClass(InMemoryProfileStore.class);
        addClass(InMemoryAuthorizer.class);
        addClass(InMemoryProblemStore.class);
        addClass(InMemorySubmissionStore.class);
        addClass(HardcodedSubmissions.class);
        addClass(ProfileManager.class);
        addClass(CodeSubmitter.class);
        addClass(TestContext.class);
    }

    @Override
    public void start() {
        delegate.start();
    }

    @Override
    public void stop() {
        delegate.stop();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        return delegate.addClass(aClass);
    }

    @Override
    public <T> T getInstance(Class<T> aClass) {
        return delegate.getInstance(aClass);
    }
}
