module.exports = {
  publicPath: '/',
  lintOnSave: false,
  devServer: {
    port: 7070,
    open: true,
    proxy: {
      '/url': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true,
        secure: false,
        pathRewrite: {
          '^/url': ''
        }
      }
    }
  }
}
