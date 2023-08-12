import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import {BrowserRouter} from "react-router-dom";
import {HelmetProvider} from "react-helmet-async";

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
        <BrowserRouter>
            <HelmetProvider>
                <App />
            </HelmetProvider>
        </BrowserRouter>
);
