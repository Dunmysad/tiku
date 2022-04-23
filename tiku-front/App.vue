<script>
	export default {
		globalData:{
			skShowData:{
				skShowM:'v',
				skRSAPublicKey:'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANL378k3RiZHWx5AfJqdH9xRNBmD9wGD\n' +
  '2iRe41HdTNF8RUhNnHit5NpMNtGL0NPTSSpPjjI1kJfVorRvaQerUgkCAwEAAQ=='
			},
		/*临时图片地址*/
		testUrl: "http://localhost:8000/api"
		},
		
	}
</script>

<style lang="scss">
	@import 'uview-ui/theme.scss';
	/*每个页面公共css */
	/* 引入字体图标 */
	// @import url("./static/fonts/iconfont.css");
	.page{
		height: 100vh !important;
		background-color: rgb(206, 231, 225);
	}
	
	/* 覆盖样式修改颜色 */
	checkbox.theme[checked] .wx-checkbox-input,
	radio.theme[checked] .wx-radio-input,
	checkbox.theme.checked .uni-checkbox-input,
	radio.theme.checked .uni-radio-input {
	    background-color: rgb(0, 122, 255) !important;
	    border-color: rgb(0, 122, 255) !important;
	    color: #ffffff !important;
	}
</style>
