import { createGlobalStyle } from 'styled-components';
import 'rsuite/lib/styles/index.less';

const GlobalStyle = createGlobalStyle`
    body {
        height: 100%;
        margin: 0;
    }
`;

export default GlobalStyle;