package org.simon.autoet.track;

/**
 * 测试结果实体类
 *
 * @author simon
 * @version V1.0
 * @since 2017/10/28 12:27
 */
public class Result {

    private long took;
    private long total;
    private long error;
    private double throughout;
    private double minThroughout;
    private double maxThroughout;
    private double errorRate;
    private String type;

    public Result() {
        //    需要默认构造函数
    }

    public Result(long took, long total, long error, double throughout, double errorRate) {
        this.took = took;
        this.total = total;
        this.error = error;
        this.throughout = throughout;
        this.errorRate = errorRate;
    }

    public Result avg(Result result) {
        if (result != null) {
            this.took = result.getTook() + this.getTook();
            this.total = result.getTotal() + this.getTotal();
            this.error = result.getError() + this.getError();
            this.throughout = (result.getThroughout() + this.getThroughout()) / 2;
            this.errorRate = (result.getErrorRate() + this.getErrorRate()) / 2;
        }
        return this;
    }

    public Result minResult(Result result){
        if (this.getThroughout()>result.getThroughout()){
            this.minThroughout=result.getThroughout();
        }
        return this;
    }

    public Result maxResult(Result result){
        if (this.getThroughout()<result.getThroughout()){
            this.maxThroughout=result.getThroughout();
        }
        return this;
    }

    public double getMinThroughout() {
        return minThroughout;
    }

    public double getMaxThroughout() {
        return maxThroughout;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTook() {
        return took;
    }

    public long getTotal() {
        return total;
    }

    public long getError() {
        return error;
    }

    public double getThroughout() {
        return throughout;
    }

    public double getErrorRate() {
        return errorRate;
    }


}
