package com.zenchn.electrombile.entity.model;

import com.zenchn.electrombile.entity.CommandModel;

public enum TcpCmdEnum {

    远程锁车() {
        @Override
        public CommandModel getTcpCmd() {
            return new CommandModel("S15", "1", 3);
        }
    },
    远程解锁() {
        @Override
        public CommandModel getTcpCmd() {
            return new CommandModel("S15", "0", 3);
        }
    },
    远程设防() {
        @Override
        public CommandModel getTcpCmd() {
            return new CommandModel("S12", "", 3);
        }
    },
    远程撤防() {
        @Override
        public CommandModel getTcpCmd() {
            return new CommandModel("S13", "", 3);
        }
    },
    远程开灯() {
        @Override
        public CommandModel getTcpCmd() {
            return new CommandModel("FINDCAR", "", 3);
        }
    },
    远程关灯() {
        @Override
        public CommandModel getTcpCmd() {
            return new CommandModel("S12", "", 3);
        }
    },
    声光寻车() {
        @Override
        public CommandModel getTcpCmd() {
            return new CommandModel("FINDCAR", "1", 1);
        }
    },
    车辆限速() {
        @Override
        public CommandModel getTcpCmd() {
            return new CommandModel("LIMITSPEED", "0", 1);
        }
    };

    public abstract CommandModel getTcpCmd();

}
