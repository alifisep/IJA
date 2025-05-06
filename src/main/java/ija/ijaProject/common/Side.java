    package ija.ijaProject.common;

    // Side of gameNode
    public enum Side {
        NORTH, EAST, SOUTH, WEST;
        public Side rotate() {
            return switch(this) {
                case NORTH -> EAST;
                case EAST  -> SOUTH;
                case SOUTH -> WEST;
                case WEST  -> NORTH;
            };
        }
    }
