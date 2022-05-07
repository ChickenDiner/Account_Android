package com.example.accountbook.ui.chart;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.accountbook.R;
import com.example.accountbook.backend.AccountInquiry;
import com.example.accountbook.backend.AccountItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class ChartsFragment extends Fragment {

    private LineChartView lineChartView;
    private PieChartView pieChartViewIncome;
    private PieChartView pieChartViewExpense;
    private LineChartData lineChartData;
    private Line incomeLine;
    private Line expenseLine;
    private Line placeholderLine;
    private Axis axisX;
    private Axis axisY;
    private PieChartData pieChartIncomeData;
    private PieChartData pieChartExpenseData;

    private LinearLayout incomeLabelWrapper;
    private LinearLayout expenseLabelWrapper;

    private TextView incomeLineLabel;
    private TextView expenseLineLabel;

    private AccountInquiry inquiry;

    static final private int LINE_CHART_EXPENSE_COLOR = 0xffff6666;
    static final private int LINE_CHART_INCOME_COLOR = 0xff99cc99;

    static final private int LINE_CHART_AXIS_X_COLOR = 0xff454545;
    static final private int LINE_CHART_AXIS_Y_COLOR = 0xff454545;

    static final private String LINE_CHART_AXIS_X_TEXT = "时间";
    static final private String LINE_CHART_AXIS_Y_TEXT = "收入/支出";

    static final private ValueShape LINE_CHART_INCOME_DOT = ValueShape.DIAMOND;
    static final private ValueShape LINE_CHART_EXPENSE_DOT = ValueShape.SQUARE;

    static final private int[] pieChartLabelColor = {
            0xff996699,
            0xff666666,
            0xff003366,
            0xffffff99,
            0xff99cc99,
            0xff6666cc
    };


    static final private int CHART_ON_UNDEFINED = 0;
    static final private int CHART_ON_MONTH = 1;
    static final private int CHART_ON_SEASON = 2;
    static final private int CHART_ON_YEAR = 3;
    static final private int statusActiveColor = 0xFF00574B;
    static final private int statusInactiveColor = Color.BLACK;
    private int chartStatus;

    private Button buttonSwitchToMonth;
    private Button buttonSwitchToSeason;
    private Button buttonSwitchToYear;

    private TextView incomeSumDisplay;
    private TextView expenseSumDisplay;

    private double income;
    private double expense;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_charts, container, false);
        /**
        final TextView textView = root.findViewById(R.id.text_notifications);
        chartsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */

        /**
         * 1711326 hrl
         * implementation of chart page
         */

        View view = root;

        // init database
        inquiry = new AccountInquiry(this.getActivity());

        // init chart status
        chartStatus = CHART_ON_UNDEFINED;

        // init chart
        lineChartView           = view.findViewById(R.id.chart_line_chart);
        pieChartViewIncome      = view.findViewById(R.id.chart_pie_chart_income);
        pieChartViewExpense     = view.findViewById(R.id.chart_pie_chart_expense);
        pieChartIncomeData      = new PieChartData();
        pieChartExpenseData     = new PieChartData();
        lineChartData           = new LineChartData();
        incomeLine              = new Line();
        expenseLine             = new Line();
        axisX                   = new Axis();
        axisY                   = new Axis();
        placeholderLine = new Line();
        placeholderLine.setHasLines(false);
        placeholderLine.setHasLabels(false);
        placeholderLine.setHasPoints(false);
        List<PointValue> placeholderValue = new ArrayList<>();
        placeholderValue.add(new PointValue(1, 0));
        placeholderValue.add(new PointValue(2, 1));
        placeholderLine.setValues(placeholderValue);

        incomeLineLabel = view.findViewById(R.id.chart_line_label_income);
        expenseLineLabel = view.findViewById(R.id.chart_line_label_expense);

        incomeLabelWrapper = view.findViewById(R.id.chart_pie_labels_income);
        expenseLabelWrapper = view.findViewById(R.id.chart_pie_labels_expense);

        // init layout
        incomeLabelWrapper      = view.findViewById(R.id.chart_pie_labels_income);
        expenseLabelWrapper     = view.findViewById(R.id.chart_pie_labels_expense);

        // init button
        buttonSwitchToMonth     = view.findViewById(R.id.chart_toggle_button_month);
        buttonSwitchToSeason    = view.findViewById(R.id.chart_toggle_button_season);
        buttonSwitchToYear      = view.findViewById(R.id.chart_toggle_button_year);

        // init textview
        incomeSumDisplay        = view.findViewById(R.id.chart_income_num);
        expenseSumDisplay       = view.findViewById(R.id.chart_expense_num);

        // configure textview
        income = 0.0;
        expense = 0.0;

        // configure chart
        // line chart
        final boolean lineChartHasLine = true;
        final boolean lineChartHasPoint = false;
        final int     axisTextSize     = 12;
        final boolean hasSplitLineX = true;
        final boolean hasSplitLineY = true;
        incomeLine.setHasPoints(lineChartHasPoint);
        expenseLine.setHasPoints(lineChartHasPoint);
        incomeLine.setHasLines(lineChartHasLine);
        expenseLine.setHasLines(lineChartHasLine);
        incomeLine.setColor(LINE_CHART_INCOME_COLOR);
        expenseLine.setColor(LINE_CHART_EXPENSE_COLOR);
        incomeLine.setShape(LINE_CHART_INCOME_DOT);
        expenseLine.setShape(LINE_CHART_EXPENSE_DOT);
        axisX.setTextSize(axisTextSize);
        axisY.setTextSize(axisTextSize);
        axisX.setTextColor(LINE_CHART_AXIS_X_COLOR);
        axisY.setTextColor(LINE_CHART_AXIS_Y_COLOR);
        axisX.setHasLines(hasSplitLineX);
        axisY.setHasLines(hasSplitLineY);
        axisX.setName(LINE_CHART_AXIS_X_TEXT);
        axisY.setName(LINE_CHART_AXIS_Y_TEXT);
        incomeLineLabel.setTextColor(LINE_CHART_INCOME_COLOR);
        expenseLineLabel.setTextColor(LINE_CHART_EXPENSE_COLOR);

        // pie chart
        final boolean pieChartHasCenterCircle         = true;
        final boolean pieChartHasLabel                = false;
        final boolean pieChartLabelOutside            = false;
        final int     pieChartCenterTextSize          = 26;
        String  pieChartCenterMessageIncome     = "收入";
        String  pieChartCenterMessageExpense    = "支出";
        pieChartIncomeData.setHasCenterCircle(pieChartHasCenterCircle);
        pieChartExpenseData.setHasCenterCircle(pieChartHasCenterCircle);
        pieChartIncomeData.setCenterText1(pieChartCenterMessageIncome);
        pieChartExpenseData.setCenterText1(pieChartCenterMessageExpense);
        pieChartIncomeData.setHasLabels(pieChartHasLabel);
        pieChartExpenseData.setHasLabels(pieChartHasLabel);
        pieChartIncomeData.setHasLabelsOutside(pieChartLabelOutside);
        pieChartExpenseData.setHasLabelsOutside(pieChartLabelOutside);
        pieChartIncomeData.setCenterText1FontSize(pieChartCenterTextSize);
        pieChartExpenseData.setCenterText1FontSize(pieChartCenterTextSize);
        // pieChartViewIncome.setPieChartData(pieChartIncomeData);
        // pieChartViewExpense.setPieChartData(pieChartExpenseData);

        // configure button
        buttonSwitchToMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chartStatus != CHART_ON_MONTH) {
                    switchTo(CHART_ON_MONTH);
                }
            }
        });
        buttonSwitchToSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chartStatus != CHART_ON_SEASON) {
                    switchTo(CHART_ON_SEASON);
                }
            }
        });
        buttonSwitchToYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chartStatus != CHART_ON_YEAR) {
                    switchTo(CHART_ON_YEAR);
                }
            }
        });

        // init data
        switchTo(CHART_ON_MONTH);

        // \
        return root;
    }

    private LinearLayout getPieChartLabel(String label, int color) {
        LinearLayout wrapper = new LinearLayout(this.getActivity());
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        TextView l1 = new TextView(this.getActivity());
        TextView l2 = new TextView(this.getActivity());
        l1.setText("  ◼ ");
        l1.setTextColor(color);
        l2.setText(label);
        l2.setTextColor(0xff333333);
        wrapper.addView(l1);
        wrapper.addView(l2);
        return wrapper;
    }

    private void addIncomePieChartLabel(String label, int color) {
        incomeLabelWrapper.addView(getPieChartLabel(label, color));
    }

    private void addExpensePieChartLabel(String label, int color) {
        expenseLabelWrapper.addView(getPieChartLabel(label, color));
    }

    private void clearPieChartLabel() {
        incomeLabelWrapper.removeAllViewsInLayout();
        expenseLabelWrapper.removeAllViewsInLayout();
    }

    private void switchTo(int status) {
        expense = 0;
        income = 0;
        clearPieChartLabel();
        buttonSwitchToMonth.setTextColor(statusInactiveColor);
        buttonSwitchToSeason.setTextColor(statusInactiveColor);
        buttonSwitchToYear.setTextColor(statusInactiveColor);
        switch (status) {
            case CHART_ON_MONTH:
                switchToMonth();
                buttonSwitchToMonth.setTextColor(statusActiveColor);
                break;
            case CHART_ON_SEASON:
                switchToSeason();
                buttonSwitchToSeason.setTextColor(statusActiveColor);
                break;
            case CHART_ON_YEAR:
                switchToYear();
                buttonSwitchToYear.setTextColor(statusActiveColor);
                break;
            case CHART_ON_UNDEFINED:
                Log.e(" - ChartFragment", "Undefined Switch behavior occurred.");
                break;
        }
        chartStatus = status;
        incomeSumDisplay.setText(Double.toString(income));
        expenseSumDisplay.setText(Double.toString(expense));


        List<Line> lines = new ArrayList<>();
        lines.add(incomeLine);
        lines.add(expenseLine);
        lines.add(placeholderLine);
        lineChartData.setLines(lines);
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);
        lineChartView.setLineChartData(lineChartData);

        pieChartViewIncome.setPieChartData(pieChartIncomeData);
        pieChartViewExpense.setPieChartData(pieChartExpenseData);
    }

    private void switchToMonth() {
        List<AxisValue> axisXLabels = new ArrayList<>();
        List<PointValue> incomePoints = new ArrayList<>();
        List<PointValue> expensePoints = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");

        // 求出最近30天
        Calendar today = Calendar.getInstance();
        Date[] days = new Date[30];
        for (int i = 29; i>=0; i--) {
            days[i] = today.getTime();
            today.add(Calendar.DATE, -1);
        }
        // 设置折线图
        for (int i=0; i < 30; i++) {
            AxisValue label = new AxisValue(i);
            label.setLabel(dateFormat.format(days[i]));
            axisXLabels.add(label);
            double incomeMoney = inquiry.inquiryIncomeSumOnDate(days[i]);
            double expenseMoney = inquiry.inquiryExpenseSumOnDate(days[i]);
            PointValue incomePoint = new PointValue(i, (float)incomeMoney);
            incomePoints.add(incomePoint);
            PointValue expensePoint = new PointValue(i, (float)expenseMoney);
            expensePoints.add(expensePoint);
            expense += expenseMoney;
            income += incomeMoney;
        }
        // 设置饼图
        List<AccountItem> incomeWithType = inquiry.inquiryIncomeSumOnTypeBetweenDate(days[0], days[29]);
        List<AccountItem> expenseWithType = inquiry.inquiryExpenseSumOnTypeBetweenDate(days[0], days[29]);

        List<SliceValue> incomePieChartValue = new ArrayList<>();
        List<SliceValue> expensePieChartValue = new ArrayList<>();

        for (int i=0; i < incomeWithType.size(); i++) {
            int labelColor = ChartUtils.nextColor();
            // ChartUtils.nextColor();
            SliceValue incomeValue = new SliceValue((float)incomeWithType.get(i).money, labelColor);
            String incomeType = incomeWithType.get(i).getType();
            incomeValue.setLabel(incomeType);
            incomePieChartValue.add(incomeValue);
            addIncomePieChartLabel(incomeType, labelColor);
        }

        for (int i=0; i < expenseWithType.size(); i++) {
            int labelColor = ChartUtils.nextColor();
            SliceValue expenseValue = new SliceValue((float)expenseWithType.get(i).money, labelColor);
            String expenseType = expenseWithType.get(i).getType();
            expenseValue.setLabel(expenseType);
            expensePieChartValue.add(expenseValue);
            addExpensePieChartLabel(expenseType, labelColor);
        }

        axisX.setValues(axisXLabels);
        incomeLine.setValues(incomePoints);
        expenseLine.setValues(expensePoints);

        pieChartIncomeData.setValues(incomePieChartValue);
        pieChartExpenseData.setValues(expensePieChartValue);
    }

    private void switchToSeason() {
        List<AxisValue> axisXLabels = new ArrayList<>();
        List<PointValue> incomePoints = new ArrayList<>();
        List<PointValue> expensePoints = new ArrayList<>();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

        Calendar cal = Calendar.getInstance();
        Date[]   begins = new Date[3];
        Date[]   ends   = new Date[3];
        for (int i=2; i>=0; i--) {
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            ends[i] =  cal.getTime();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            begins[i] = cal.getTime();
            cal.add(Calendar.MONTH, -1);
        }
        // 设置折线图
        for (int i=0; i<3; i++) {
            AxisValue label = new AxisValue(i);
            label.setLabel(Integer.valueOf(monthFormat.format(begins[i])) + "月");
            axisXLabels.add(label);
            double incomeMoney = inquiry.inquiryIncomeSumBetweenDate(begins[i], ends[i]);
            double expenseMoney = inquiry.inquiryExpenseSumBetweenDate(begins[i], ends[i]);
            PointValue incomePoint = new PointValue(i, (float)incomeMoney);
            PointValue expensePoint = new PointValue(i, (float)expenseMoney);
            incomePoints.add(incomePoint);
            expensePoints.add(expensePoint);
            expense += expenseMoney;
            income += incomeMoney;
        }

        List<AccountItem> incomeWithType = inquiry.inquiryIncomeSumOnTypeBetweenDate(begins[0], ends[2]);
        List<AccountItem> expenseWithType = inquiry.inquiryExpenseSumOnTypeBetweenDate(begins[0], ends[2]);

        List<SliceValue> incomePieChartValue = new ArrayList<>();
        List<SliceValue> expensePieChartValue = new ArrayList<>();

        for (int i=0; i < incomeWithType.size(); i++) {
            int labelColor = ChartUtils.nextColor();
            // ChartUtils.nextColor();
            SliceValue incomeValue = new SliceValue((float)incomeWithType.get(i).money, labelColor);
            String incomeType = incomeWithType.get(i).getType();
            incomeValue.setLabel(incomeType);
            incomePieChartValue.add(incomeValue);
            addIncomePieChartLabel(incomeType, labelColor);
        }

        for (int i=0; i < expenseWithType.size(); i++) {
            int labelColor = ChartUtils.nextColor();
            SliceValue expenseValue = new SliceValue((float)expenseWithType.get(i).money, labelColor);
            String expenseType = expenseWithType.get(i).getType();
            expenseValue.setLabel(expenseType);
            expensePieChartValue.add(expenseValue);
            addExpensePieChartLabel(expenseType, labelColor);
        }

        axisX.setValues(axisXLabels);
        incomeLine.setValues(incomePoints);
        expenseLine.setValues(expensePoints);

        pieChartIncomeData.setValues(incomePieChartValue);
        pieChartExpenseData.setValues(expensePieChartValue);
    }

    private void switchToYear() {
        List<AxisValue> axisXLabels = new ArrayList<>();
        List<PointValue> incomePoints = new ArrayList<>();
        List<PointValue> expensePoints = new ArrayList<>();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

        Calendar cal = Calendar.getInstance();
        Date[]   begins = new Date[12];
        Date[]   ends   = new Date[12];
        for (int i=11; i>=0; i--) {
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            ends[i] =  cal.getTime();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            begins[i] = cal.getTime();
            cal.add(Calendar.MONTH, -1);
        }
        // 设置折线图
        for (int i=0; i<12; i++) {
            AxisValue label = new AxisValue(i);
            label.setLabel(Integer.valueOf(monthFormat.format(begins[i])) + "月");
            axisXLabels.add(label);
            double incomeMoney = inquiry.inquiryIncomeSumBetweenDate(begins[i], ends[i]);
            double expenseMoney = inquiry.inquiryExpenseSumBetweenDate(begins[i], ends[i]);
            PointValue incomePoint = new PointValue(i, (float)incomeMoney);
            PointValue expensePoint = new PointValue(i, (float)expenseMoney);
            incomePoints.add(incomePoint);
            expensePoints.add(expensePoint);
            expense += expenseMoney;
            income += incomeMoney;
        }

        List<AccountItem> incomeWithType = inquiry.inquiryIncomeSumOnTypeBetweenDate(begins[0], ends[11]);
        List<AccountItem> expenseWithType = inquiry.inquiryExpenseSumOnTypeBetweenDate(begins[0], ends[11]);

        List<SliceValue> incomePieChartValue = new ArrayList<>();
        List<SliceValue> expensePieChartValue = new ArrayList<>();

        for (int i=0; i < incomeWithType.size(); i++) {
            int labelColor = ChartUtils.nextColor();
            // ChartUtils.nextColor();
            SliceValue incomeValue = new SliceValue((float)incomeWithType.get(i).money, labelColor);
            String incomeType = incomeWithType.get(i).getType();
            incomeValue.setLabel(incomeType);
            incomePieChartValue.add(incomeValue);
            addIncomePieChartLabel(incomeType, labelColor);
        }

        for (int i=0; i < expenseWithType.size(); i++) {
            int labelColor = ChartUtils.nextColor();
            SliceValue expenseValue = new SliceValue((float)expenseWithType.get(i).money, labelColor);
            String expenseType = expenseWithType.get(i).getType();
            expenseValue.setLabel(expenseType);
            expensePieChartValue.add(expenseValue);
            addExpensePieChartLabel(expenseType, labelColor);
        }

        axisX.setValues(axisXLabels);
        incomeLine.setValues(incomePoints);
        expenseLine.setValues(expensePoints);

        pieChartIncomeData.setValues(incomePieChartValue);
        pieChartExpenseData.setValues(expensePieChartValue);
    }

}