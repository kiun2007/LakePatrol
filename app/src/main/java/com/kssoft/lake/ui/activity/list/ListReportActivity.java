//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.kssoft.lake.ui.activity.list;

import android.content.Context;
import android.widget.Toast;

import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityListReportBinding;
import com.kssoft.lake.net.requests.dto.RepCkDto;
import com.kssoft.lake.net.requests.dto.ReportDto;
import com.kssoft.lake.net.responses.vo.ReportVo;
import com.kssoft.lake.net.responses.vo.TreeVo;
import com.kssoft.lake.net.services.CommitService;
import com.kssoft.lake.ui.activity.leader.WordBrowseActivityHandler;
import com.kssoft.lake.utils.ListViewUtil;
import java.util.Date;
import java.util.List;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.utils.AlertUtil;
import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.ListHandlerUtil;
import kiun.com.bvroutine.views.dialog.MCDialogManager;

public class ListReportActivity extends RequestBVActivity<ActivityListReportBinding> {
    public static final Class clz = ListReportActivity.class;
    ListHandler<ReportVo> listHandler = new ListHandler<ReportVo>(BR.handler, R.layout.list_error_normal) {
        public void onClick(Context context, int tag, ReportVo reportVo) {
            if (tag == 0) {
                WordBrowseActivityHandler.openActivityNormal(context, reportVo.getRnm());
            } else {
                //同意
                if (tag == 1) {
                    if ("0".equals(reportVo.getRst())) {

                        AlertUtil.build(ListReportActivity.this.getContext(), "是否通过简报《%s》审核?", reportVo.getTitle())
                                .setPositiveButton("确定", (dialog, which) -> {
                                    changeRepCk(new RepCkDto(reportVo.getRcd()){{setRst(4);}});
                                }).show();
                        return;
                    }

                    if ("2".equals(reportVo.getRst())) {
                        AlertUtil.build(ListReportActivity.this.getContext(), "是否通过简报《%s》审核,通过后将发布?", reportVo.getTitle())
                                .setPositiveButton("确定", (dialog, which) -> {
                                    changeRepCk(new RepCkDto(reportVo.getRcd()){{setRst(4);}});
                                }).show();
                    }
                } else if (tag == 2) {
                    RepCkDto refuse = (new RepCkDto(reportVo.getRcd())).refuse(reportVo.getRst());
                    MCDialogManager.create(ListReportActivity.this.getContext(), R.layout.dialog_report_refuse, reportVo)
                            .setGravity(17)
                            .setCancelable(true)
                            .transparent()
                            .show()
                            .setCaller(v -> {
                                changeRepCk(refuse);
                            })
                            .bindValue(BR.repCkDto, refuse);
                } else if (tag == 3) {
                    RepCkDto refuse = new RepCkDto(reportVo.getRcd()).release();
                    AlertUtil.build(getContext(), "是否发起简报《%s》审核?", reportVo.getTitle())
                            .setPositiveButton("确定", (dialog, which) -> {
                                changeRepCk(refuse);
                            }).show();
                }
            }
        }
    };

    private void changeRepCk(RepCkDto repCkDto){
        addRequest(()->rbp.callServiceData(CommitService.class, s -> s.addReportPerson(repCkDto)), ListReportActivity.this::onSuccess);
    }

    private void onDataDtoChanged(ReportDto var1) {
        ListViewUtil.refresh(this.listHandler);
    }

    private void onSuccess(Object var1) {
        ListHandlerUtil.refresh(this.listHandler);
        Toast.makeText(this.getContext(), "操作成功", Toast.LENGTH_LONG).show();
    }

    private void treeDataComplete(List<TreeVo> var1, ReportVo reportVo) {
        MCDialogManager mcDialogManager = MCDialogManager.create(this.getContext(), 2131492950, var1);
        mcDialogManager.setGravity(80).setCancelable(true).show();
//        var3.setCaller(new 41ILrQWotcijM_p2Eknar0G79bA(this, var3, var2));
    }

    public int getViewId() {
        return R.layout.activity_list_report;
    }

    public void initView() {
        binding.setHandler(this.listHandler);
//        this.getBarItem().getHandler().setRightCallBack(new zlsVs6LBbyTkxXWKG5yl3s4E-Sw(this, reportDto));
    }
}