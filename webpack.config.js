const path = require('path');
//plugin for automatic generation of index html file
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {

    entry: {
        myApp : [path.resolve(__dirname, "src/main/javascript/app", "index.js")]
    },

    output: {
        path: __dirname + '/target/classes/static',
        filename: "[name].bundle.js"
    },

    resolve: {
      mainFields: ['module', 'main'],
      extensions: ['.js', '.jsx', '.json', '.css']
    },

    module: {
      rules: [
          {
            test: /\.(js|jsx)$/,
            exclude: /node_modules/,
            loader: "babel-loader",
            query: {
              presets: [
                '@babel/preset-env',
                '@babel/preset-react'
              ]
            }
          },
          {
            test: /\.css$/,
            use: [
              {
                loader: "style-loader"
              },
              {
                loader: "css-loader",
                options: {
                  modules: true,
                }
              }
            ]
          }
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({
            hash: true,
            template: './src/main/resources/static/admin.template.html',    //use own html template
            //myVar1: 'value 1',    //define custom variable which is used in custom template
            //myVar2: 'value 2',
            //favicon: './favicon.ico',
            filename: './admin.html' //relative to root of the application
        })
   ],

   devtool: 'inline-source-map'

};
