import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import logger from 'redux-logger';
import { BrowserRouter } from 'react-router-dom';
import { applyMiddleware, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga';
import rootReducer from './reducers/rootReducer';
import { Provider } from 'react-redux';
import GlobalStyle from "./common/style";
import rootSaga from './saga/rootSaga';

const basename = "/";

const sagaMiddleware = createSagaMiddleware();
const middleware = applyMiddleware(logger, sagaMiddleware);
const store = createStore(rootReducer, middleware);
sagaMiddleware.run(rootSaga);
// or 'rsuite/dist/styles/rsuite-default.css'

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter basename={basename}>
            <GlobalStyle />
            <App/>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
