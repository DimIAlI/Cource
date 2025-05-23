//package org.example.model;
//
//import lombok.SneakyThrows;
//import org.example.exceptions.IdNotFoundException;
//import org.example.exceptions.PlaceAlreadyExistException;
//import org.example.model.dto.ReservationDto;
//import org.example.model.dto.SpaceTypeDto;
//import org.example.model.dto.WorkspaceDto;
//import org.example.model.entity.ReservationEntity;
//import org.example.model.entity.SpaceTypeEntity;
//import org.example.model.entity.WorkspaceEntity;
//import org.example.model.service.ReservationManager;
//import org.example.model.service.SpaceTypeManager;
//import org.example.model.service.WorkspaceManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.lang.reflect.Field;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.BDDMockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class WorkspaceManagerTest {
//
//    private final WorkspaceManager workspaceManager = WorkspaceManager.getInstance();
//    @Mock
//    private ReservationManager reservationManager;
//    private static final LocalDateTime START_TIME = LocalDateTime.now().minusHours(1);
//    private static final LocalDateTime END_TIME = LocalDateTime.now();
//
//    @BeforeEach
//    @SneakyThrows
//    void setUp() {
//        clearWorkspaceManagerFields();
//    }
//
//    @Test
//    @DisplayName("Should return a non-null instance when it called")
//    void getInstance_shouldReturnNonNullInstance_whenCalled() {
//
//        //given
//
//        //when
//        WorkspaceManager instance = WorkspaceManager.getInstance();
//
//        //then
//        assertThat(instance).isNotNull();
//    }
//
//    @Test
//    @DisplayName("Should return the same instance when called multiple times")
//    void getInstance_shouldReturnSameInstance_whenCalledMultipleTimes() {
//
//        //given
//
//        //when
//        WorkspaceManager firstCallInstance = WorkspaceManager.getInstance();
//        WorkspaceManager secondCallInstance = WorkspaceManager.getInstance();
//        WorkspaceManager thirdCallInstance = WorkspaceManager.getInstance();
//
//        //then
//        assertThat(firstCallInstance).isEqualTo(secondCallInstance).isEqualTo(thirdCallInstance);
//
//    }
//
//    @ParameterizedTest(name = "Price: {0}, Space Type: {1}")
//    @MethodSource("provideUniqueWorkspaceData")
//    @DisplayName("Should add a new workspace and assign correct parameters when place does not exist")
//    void add_shouldAddNewWorkspace_andAssignCorrectParameters_whenPlaceDoesNotExist(double price, SpaceTypeDto type) {
//
//        //given
//
//        //when
//        workspaceManager.add(type, price);
//        WorkspaceDto addedWorkspace = workspaceManager.getWorkspace(workspaceManager.getId());
//
//        //then
//        assertThat(addedWorkspace).isNotNull();
//        assertThat(addedWorkspace.getId()).isGreaterThan(0);
//        assertThat(addedWorkspace.getTypeId()).isEqualTo(type);
//        assertThat(addedWorkspace.getPrice()).isEqualTo(price);
//    }
//
//    @ParameterizedTest(name = "Unique Price: {0}, Repeated Price: {1}, Unique Space Type: {2}, Repeated Space Type: {3}")
//    @MethodSource("provideNonUniqueWorkspaceData")
//    @DisplayName("Should throw PlaceAlreadyExistException when place already exists")
//    void add_shouldThrowPlaceAlreadyExistException_whenPlaceAlreadyExists(double firstPrice, double secondPrice, SpaceTypeEntity firstType, SpaceTypeEntity secondType) {
//
//        //given
//
//        //when
//        workspaceManager.add(firstType, firstPrice);
//
//        //then
//        assertThatThrownBy(() -> workspaceManager.add(secondType, secondPrice)).isInstanceOf(PlaceAlreadyExistException.class);
//    }
//
//    @Test
//    @DisplayName("Should remove existing workspace when id is valid")
//    void remove_shouldRemoveWorkspace_whenIdExists() {
//
//        //given
//        addWorkspaces();
//        long id = generateRandomNumberBetweenOneAndIdRange(workspaceManager.getId());
//
//        //when
//        workspaceManager.remove(id);
//
//        //then
//        assertThatThrownBy(() -> workspaceManager.getWorkspace(id)).isInstanceOf(IdNotFoundException.class);
//    }
//
//    @Test
//    @DisplayName("Should throw IdNotFoundException when id does not exist")
//    void remove_shouldThrowIdNotFoundException_whenIdDoesNotExist() {
//
//        //given
//        addWorkspaces();
//        long id = getRandomNumberExcludingOneAndIdRange(workspaceManager.getId());
//
//        //when
//
//        //then
//        assertThatThrownBy(() -> workspaceManager.remove(id)).isInstanceOf(IdNotFoundException.class);
//    }
//
//    @Test
//    @DisplayName("Should return all workspaces when there are no reservations")
//    void getAvailable_shouldReturnAllWorkspaces_whenNoReservations() {
//
//        //given
//        addWorkspaces();
//
//        //when
//        List<WorkspaceDto> availableSpaces = workspaceManager.getAvailable(START_TIME, END_TIME);
//
//        //then
//        assertThat(availableSpaces).isNotNull();
//        assertThat(availableSpaces).hasSize(5);
//    }
//
//    @Test
//    @DisplayName("Should return only available workspaces at the time range when some are reserved")
//    void getAvailable_shouldReturnOnlyAvailableWorkspacesAtTimeRange_whenSomeAreReserved() {
//
//        // given
//        addWorkspaces();
//        long workspaceIdForReservation = 2;
//
//        WorkspaceDto reservedSpace = workspaceManager.getWorkspace(workspaceIdForReservation);
//
//        try (MockedStatic<ReservationManager> reservationManagerMockedStatic = Mockito.mockStatic(ReservationManager.class)) {
//            given(ReservationManager.getInstance()).willReturn(reservationManager);
//
//            ReservationDto reservation = Mockito.mock(ReservationDto.class);
//
//            given(reservation.getSpace()).willReturn(reservedSpace);
//            given(reservation.getStartTime()).willReturn(START_TIME);
//            given(reservation.getEndTime()).willReturn(END_TIME);
//
//            given(reservationManager.getAll()).willReturn(Map.of(1L, reservation));
//
//            //when
//            List<WorkspaceDto> availableWorkspaces = workspaceManager.getAvailable(START_TIME, END_TIME);
//
//            //then
//            assertThat(availableWorkspaces).hasSize(4);
//            assertThat(availableWorkspaces).doesNotContain(reservedSpace);
//        }
//    }
//
//    @Test
//    @DisplayName("getAvailable should return empty list when all workspaces are reserved")
//    void getAvailable_shouldReturnEmptyList_whenAllWorkspacesAreReserved() {
//
//        // given
//        SpaceTypeDto type = SpaceTypeManager.getInstance().getValues().get("OPEN_SPACE");
//        workspaceManager.add(type, 1000);
//
//        WorkspaceDto reservedSpace = workspaceManager.getWorkspace(1);
//
//        try (MockedStatic<ReservationManager> reservationManagerMockedStatic = Mockito.mockStatic(ReservationManager.class)) {
//            given(ReservationManager.getInstance()).willReturn(reservationManager);
//
//            ReservationEntity reservation = Mockito.mock(ReservationEntity.class);
//
//            given(reservation.getSpace()).willReturn(reservedSpace);
//            given(reservation.getStartTime()).willReturn(START_TIME);
//            given(reservation.getEndTime()).willReturn(END_TIME);
//
//            given(reservationManager.getAll()).willReturn(Map.of(1L, reservation));
//
//            //when
//            List<WorkspaceDto> availableWorkspaces = workspaceManager.getAvailable(START_TIME, END_TIME);
//
//            //then
//            assertThat(availableWorkspaces).isEmpty();
//        }
//    }
//
//    @ParameterizedTest(name = "Price: {0}, Space Type: {1}")
//    @MethodSource("provideUniqueWorkspaceData")
//    @DisplayName("Should return workspace when ID exists")
//    void getWorkspace_shouldReturnWorkspace_whenIdExists(double price, SpaceTypeEntity type) {
//
//        //given
//        workspaceManager.add(type, price);
//        long id = workspaceManager.getId();
//
//        // when
//        WorkspaceDto result = workspaceManager.getWorkspace(id);
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(id);
//        assertThat(result.getPrice()).isEqualTo(price);
//        assertThat(result.getTypeId()).isEqualTo(type);
//    }
//
//    @Test
//    @DisplayName("Should throw IdNotFoundException when ID does not exist")
//    void getWorkspace_shouldThrowIdNotFoundException_whenIdDoesNotExist() {
//
//        //given
//        addWorkspaces();
//        long notExistedId = 999L;
//
//        //when
//
//        //then
//        assertThatThrownBy(() -> workspaceManager.getWorkspace(notExistedId)).isInstanceOf(IdNotFoundException.class);
//    }
//
//    private static Long generateRandomNumberBetweenOneAndIdRange(long id) {
//        Random random = new Random();
//        return 1 + (Math.abs(random.nextLong()) % id);
//    }
//
//    private static long getRandomNumberExcludingOneAndIdRange(long id) {
//        Random random = new Random();
//        return id + 1 + Math.abs(random.nextLong());
//    }
//
//    private static Stream<Arguments> provideUniqueWorkspaceData() {
//        Map<String, SpaceTypeDto> types = SpaceTypeManager.getInstance().getValues();
//        return Stream.of(
//                Arguments.of(100.1, types.get("EVENT_SPACE")),
//                Arguments.of(100.1, types.get("MEETING_ROOM")),
//                Arguments.of(150, types.get("DEDICATED_DESK")),
//                Arguments.of(150, types.get("PRIVATE_OFFICE")),
//                Arguments.of(200.20, types.get("PRIVATE_OFFICE")));
//    }
//
//    private static Stream<Arguments> provideNonUniqueWorkspaceData() {
//        Map<String, SpaceTypeDto> types = SpaceTypeManager.getInstance().getValues();
//        return Stream.of(
//                Arguments.of(100.1, 100.1, types.get("EVENT_SPACE"), types.get("EVENT_SPACE"),
//                        Arguments.of(150, 150, types.get("DEDICATED_DESK"), types.get("DEDICATED_DESK")),
//                        Arguments.of(200.20, 200.20, types.get("PRIVATE_OFFICE"), types.get("PRIVATE_OFFICE")))
//        );
//    }
//
//    private void clearWorkspaceManagerFields() {
//        clearWorkspacesField();
//        clearIdField();
//    }
//
//    @SneakyThrows
//    private void clearWorkspacesField() {
//        Class<WorkspaceManager> workspaceManagerClass = WorkspaceManager.class;
//        Field managerField = workspaceManagerClass.getDeclaredField("WORKSPACES");
//        managerField.setAccessible(true);
//        managerField.set(workspaceManager, new HashMap<Long, WorkspaceEntity>());
//    }
//
//    @SneakyThrows
//    private void clearIdField() {
//        Class<WorkspaceManager> workspaceManagerClass = WorkspaceManager.class;
//        Field managerField = workspaceManagerClass.getDeclaredField("id");
//        managerField.setAccessible(true);
//        managerField.set(workspaceManager, 0L);
//    }
//
//    private void addWorkspaces() {
//        Map<String, SpaceTypeDto> types = SpaceTypeManager.getInstance().getValues();
//        workspaceManager.add(types.get("OPEN_SPACE"), 1000);
//        workspaceManager.add(types.get("HOT_DESK"), 222.01);
//        workspaceManager.add(types.get("SHARED_OFFICE"), 11.11);
//        workspaceManager.add(types.get("MEETING_ROOM"), 20);
//        workspaceManager.add(types.get("OPEN_SPACE"), 100);
//    }
//}