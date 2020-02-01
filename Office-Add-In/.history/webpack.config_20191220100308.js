const devCerts = require("office-addin-dev-certs");
const { CleanWebpackPlugin } = require("clean-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const fs = require("fs");
const webpack = require("webpack");

module.exports = async (env, options) => {
  const dev = options.mode === "development";
  const config = {
    devtool: "source-map",
    entry: {
      polyfill: "@babel/polyfill",
      taskpane: "./src/taskpane/taskpane.js",
      commands: "./src/commands/commands.js",
      home: "./src/home/home.js"
    },
    resolve: {
      extensions: [".ts", ".tsx", ".html", ".js"]
    },
    module: {
      rules: [
        {
          test: /\.js$/,
          exclude: /node_modules/,
          use: {
            loader: "babel-loader", 
            options: {
              presets: ["@babel/preset-env"]
            }
          }
        },
        {
          test: /\.html$/,
          exclude: /node_modules/,
          use: "html-loader"
        },
        {
          test: /\.(png|jpg|jpeg|gif)$/,
          use: "file-loader"
        }
      ]
    },
    plugins: [
      new CleanWebpackPlugin(),
      new HtmlWebpackPlugin({
        filename: "taskpane.html",
        template: "./src/taskpane/taskpane.html",
        chunks: ["polyfill", "taskpane"]
      }),
      new CopyWebpackPlugin([
        {
          to: "taskpane.css",
          from: "./src/taskpane/taskpane.css"
        }
      ]),
      new HtmlWebpackPlugin({
        filename: "commands.html",
        template: "./src/commands/commands.html",
        chunks: ["polyfill", "commands"]
      }),
      new HtmlWebpackPlugin({
          filename: "home.html",
          template: "./src/home/home.html",
          chunks: ["polyfill", "home"]
      }),
      new HtmlWebpackPlugin({
        filename: "project.html",
        template: "./src/project/project.html",
        chunks: ["polyfill", "project"]
    })
    ],
    devServer: {
      headers: {
        "Access-Control-Allow-Origin": "*"
      },      
      https: (options.https !== undefined) ? options.https : await devCerts.getHttpsServerOptions(),
      port: process.env.npm_package_config_dev_server_port || 3000,
      proxy: [
        {
          context: ['/office/login'],
          target: 'http://localhost:8089',
          changeOrigin: true,
          secure: false
        },
        {
          context: ['/office/*'],
          target: 'http://localhost:8089',
          changeOrigin: true,
          secure: false
        },
        {
          context: ['/reports/*'],
          target: 'http://localhost:8089',
          changeOrigin: true,
          secure: false
        },
        {
          context: ['/reportDetail/*'],
          target: 'http://localhost:8089',
          changeOrigin: true,
          secure: false
        }   
      ]
    }
  };

  return config;
};
