package com.zenchn.electrombile.entity.model;

import com.zenchn.electrombile.ui.activity.InsuranceActivateActivity;
import com.zenchn.electrombile.ui.activity.InsuranceApplyStatusActivity;
import com.zenchn.electrombile.ui.activity.InsurancePayActivity;
import com.zenchn.electrombile.ui.activity.InsuranceProtectActivity;

/**
 * 作    者：wangr on 2017/2/24 15:34
 * 描    述：
 * 修订记录：
 */
public enum InsuranceStatusEnum {

    未购买 {
        @Override
        public Class getActivity() {
            return InsurancePayActivity.class;
        }
    },
    未启用 {
        @Override
        public Class getActivity() {
            return InsuranceActivateActivity.class;
        }
    },
    审核中 {
        @Override
        public Class getActivity() {
            return InsuranceApplyStatusActivity.class;
        }
    },
    审核未通过 {
        @Override
        public Class getActivity() {
            return InsuranceApplyStatusActivity.class;
        }
    },
    保障中 {
        @Override
        public Class getActivity() {
            return InsuranceProtectActivity.class;
        }
    },
    理赔受理中 {
        @Override
        public Class getActivity() {
            return null;
        }
    },
    理赔未通过 {
        @Override
        public Class getActivity() {
            return null;
        }
    },
    已出险 {
        @Override
        public Class getActivity() {
            return InsurancePayActivity.class;
        }
    },
    已到期 {
        @Override
        public Class getActivity() {
            return InsurancePayActivity.class;
        }
    };

    public abstract Class getActivity();

    public static InsuranceStatusEnum classifyInsuranceStatus(int insuranceStatus) {
        for (InsuranceStatusEnum insuranceStatusEnum : values()) {
            if (insuranceStatusEnum.ordinal() == insuranceStatus)
                return insuranceStatusEnum;
        }
        return InsuranceStatusEnum.已到期;
    }
}
