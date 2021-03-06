package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersService}
 */
@PrepareForTest({SingleUserRequestAdapter.class, BatchUsersRequestAdapter.class, UsersService.class})
public class UsersServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private SingleUserRequestAdapter singleUserRequestAdapterMock;

    @Mock
    private BatchUsersRequestAdapter batchUsersRequestAdapterMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_set_ord_id (){
        UsersService service = new UsersService(sessionStorageMock);
        UsersService retrieved = service.organization("MyCoolOrg");

        assertSame(retrieved, service);
        assertEquals(Whitebox.getInternalState(service, "organizationId"), "MyCoolOrg");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_not_specified_ord_name (){
        UsersService service = new UsersService(sessionStorageMock);
        UsersService retrieved = service.organization("");
    }

    @Test
    public void should_return_proper_user_adapter_when_invoke_username_method() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        PowerMockito.whenNew(SingleUserRequestAdapter.class).withArguments(sessionStorageMock, null, "Simon")
                .thenReturn(singleUserRequestAdapterMock);

        SingleUserRequestAdapter retrieved = service.username("Simon");
        assertSame(retrieved, singleUserRequestAdapterMock);
        //verifyNew(SingleUserRequestAdapter.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_username_not_specified() {
        UsersService service = new UsersService(sessionStorageMock);
        service.username("");
    }

    @Test
    public void should_return_proper_user_adapter_() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        PowerMockito.whenNew(SingleUserRequestAdapter.class).withArguments(eq(sessionStorageMock),
                anyString()).thenReturn(singleUserRequestAdapterMock);
        SingleUserRequestAdapter retrieved = service.user();
        assertSame(retrieved, singleUserRequestAdapterMock);
        //verifyNew(SingleUserRequestAdapter.class);
    }

    @Test
    public void should_return_proper_user_adapter() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        SingleUserRequestAdapter retrieved = service.user("Simon");
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_BatchUsersRequestAdapter() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        PowerMockito.whenNew(BatchUsersRequestAdapter.class).withArguments(eq(sessionStorageMock),
                anyString()).thenReturn(batchUsersRequestAdapterMock);
        BatchUsersRequestAdapter retrieved = service.allUsers();

        assertNotNull(retrieved);
        assertSame(retrieved, batchUsersRequestAdapterMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, singleUserRequestAdapterMock, batchUsersRequestAdapterMock);
    }
}