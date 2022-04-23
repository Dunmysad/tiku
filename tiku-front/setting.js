module.exports = {
  /**
   * @description token key
   */
  TokenKey: 'SHOOTING-APP-TOKEN',
  /**
   * @description 请求超时时间，毫秒（默认2分钟）
   */
  timeout: 1200000,
  /**
   * @description 开发环境请求地址
   */
  developUrl: 'http://127.0.0.1:8000/api',
  developWsUrl: 'ws://127.0.0.1:8000/ws',
  
  productionUrl: 'http://api.mfei.fun/api',
  productionWsUrl: 'ws://api.mfei.fun/ws'
}