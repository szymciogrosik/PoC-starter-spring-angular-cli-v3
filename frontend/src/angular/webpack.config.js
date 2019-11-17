const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

module.exports = {
    entry: './src/main.ts',
    module: {
        rules: [
            {
                test: /\.ts$/,
                use: ['ts-loader', 'angular2-template-loader'],
                exclude: /node_modules/
            },
            {
                test: /\.(html|css)$/,
                loader: 'raw-loader'
            },
            {
              // Added only for resolve angular warning
              // Mark files inside `@angular/core` as using SystemJS style dynamic imports.
              // Removing this will cause deprecation warnings to appear.
              test: /[\/\\]@angular[\/\\]core[\/\\].+\.js$/,
              parser: { system: true },
            },
        ]
    },
    resolve: {
        extensions: ['.ts', '.js'],
        alias: {
            '@': path.resolve(__dirname, 'src/app/'),
        }
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './src/index.html',
            filename: 'index.html',
            inject: 'body'
        }),
        new webpack.DefinePlugin({
            // global app config object
            config: JSON.stringify({
                BACKEND_URL: 'http://localhost:8080',
                TOKEN_NAME: 'loggedUser',
            })
        }),
        // Added only for resolve angular warning
        new webpack.ContextReplacementPlugin(
          /angular([\\/])core/,
          path.resolve(__dirname, 'src/app/'),
          {}
        )
    ],
    optimization: {
        splitChunks: {
            chunks: 'all',
        },
        runtimeChunk: true
    },
    devServer: {
        historyApiFallback: true
    }
};
