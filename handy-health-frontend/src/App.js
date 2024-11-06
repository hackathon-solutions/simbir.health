import './App.css';
import LoginPage from "./pages/LoginPage";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import AuthorityProxy from "./pages/AuthorityProxy";
import ProfilePage from "./pages/ProfilePage";
import AvailableAppointmentsPage from "./pages/AvailableAppointmentsPage";
import AppointmentsPage from "./pages/AppointmentsPage";
import HospitalsPage from "./pages/HospitalsPage";
import HospitalTimetablesPage from "./pages/HospitalTimetablesPage";
import LogoutPage from "./pages/LogoutPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/profile" />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/profile" element={<AuthorityProxy loginPath="/login" componentIfAuth={<ProfilePage />} />}/>
                <Route path="/appointments/available" element={<AuthorityProxy loginPath="/login" componentIfAuth={<AvailableAppointmentsPage />} />}/>
                <Route path="/my/appointments" element={<AuthorityProxy loginPath="/login" componentIfAuth={<AppointmentsPage />} />} />
                <Route path="/hospitals" element={<AuthorityProxy loginPath="/login" componentIfAuth={<HospitalsPage />} />} />
                <Route path="/timetables/hospitals/:id" element={<AuthorityProxy loginPath="/login" componentIfAuth={<HospitalTimetablesPage />} />} />
                <Route path="/logout" element={<AuthorityProxy loginPath="/login" componentIfAuth={<LogoutPage />} />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
