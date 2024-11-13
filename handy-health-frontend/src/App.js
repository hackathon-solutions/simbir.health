import './App.css';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import AuthorityProxy from "./pages/AuthorityProxy";
import ProfilePage from "./pages/ProfilePage";
import AvailableAppointmentsPage from "./pages/AvailableAppointmentsPage";
import AppointmentsPage from "./pages/AppointmentsPage";
import HospitalsPage from "./pages/HospitalsPage";
import HospitalTimetablesPage from "./pages/HospitalTimetablesPage";
import LogoutPage from "./pages/LogoutPage";
import LoginRegPage from "./pages/LoginRegPage";
import AccountsPage from "./pages/AccountsPage";
import ReportsPage from "./pages/ReportsPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/profile" />} />
                <Route path="/accounts" element={<AuthorityProxy loginPath="/login" componentIfAuth={<AccountsPage />} />} />
                <Route path="/login" element={<LoginRegPage />} />
                <Route path="/profile" element={<AuthorityProxy loginPath="/login" componentIfAuth={<ProfilePage />} />}/>
                <Route path="/appointments/available" element={<AuthorityProxy loginPath="/login" componentIfAuth={<AvailableAppointmentsPage />} />}/>
                <Route path="/my/appointments" element={<AuthorityProxy loginPath="/login" componentIfAuth={<AppointmentsPage />} />} />
                <Route path="/hospitals" element={<AuthorityProxy loginPath="/login" componentIfAuth={<HospitalsPage />} />} />
                <Route path="/timetables/hospitals/:id" element={<AuthorityProxy loginPath="/login" componentIfAuth={<HospitalTimetablesPage />} />} />
                <Route path="/reports" element={<AuthorityProxy loginPath="/login" componentIfAuth={<ReportsPage />} />} />
                <Route path="/logout" element={<AuthorityProxy loginPath="/login" componentIfAuth={<LogoutPage />} />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
