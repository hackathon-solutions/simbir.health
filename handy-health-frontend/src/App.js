import './App.css';
import LoginPage from "./pages/LoginPage";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import AuthorityProxy from "./pages/AuthorityProxy";
import ProfilePage from "./pages/ProfilePage";
import AvailableAppointmentsPage from "./pages/AvailableAppointmentsPage";
import AppointmentsPage from "./pages/AppointmentsPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/profile" element={<AuthorityProxy loginPath="/login" componentIfAuth={<ProfilePage />} />}/>
                <Route path="/appointments/available" element={<AuthorityProxy loginPath="/login" componentIfAuth={<AvailableAppointmentsPage />} />}/>
                <Route path="/my/appointments" element={<AuthorityProxy loginPath="/login" componentIfAuth={<AppointmentsPage />} />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
