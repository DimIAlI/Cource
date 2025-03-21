package org.example.model.entity;

import lombok.Getter;

@Getter
public enum SpaceType {
    OPEN_SPACE("Open Space"),
    DEDICATED_DESK("Dedicated Desk"),
    PRIVATE_OFFICE("Private Office"),
    MEETING_ROOM("Meeting Room"),
    EVENT_SPACE("Event Space"),
    HOT_DESK("Hot Desk"),
    SHARED_OFFICE("Shared Office");
    private final String displayName;

    SpaceType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
