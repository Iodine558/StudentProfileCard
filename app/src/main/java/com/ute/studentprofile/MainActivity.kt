package com.ute.studentprofile

import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ute.studentprofile.databinding.ActivityMainBinding
import com.ute.studentprofile.model.Student
import com.ute.studentprofile.utils.toAcademicRanking
import com.ute.studentprofile.utils.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentStudent = Student(
        id = "2415053122211",
        name = "Phạm Nguyễn Minh Hải",
        className = "126LTTD01",
        email = "2415053122211@sv.ute.udn.vn",
        phone = "0987654321",
        gpa = 3.8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Gán dữ liệu ban đầu lên các Views
        bindStudentData(currentStudent)

        // Xử lý sự kiện Gọi điện
        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:${currentStudent.phone}".toUri()
            }
            startActivity(intent)
        }

        // Xử lý sự kiện khi người dùng bấm nút Cập Nhật
        binding.btnUpdateGpa.setOnClickListener {
            val inputStr = binding.edtNewGpa.text.toString().trim()
            val newGpa = inputStr.toDoubleOrNull()
            if (newGpa == null || newGpa !in 0.0..4.0) {
                // Báo lỗi nếu nhập sai định dạng hoặc ngoài khoảng 0.0 - 4.0
                binding.edtNewGpa.error = getString(R.string.invalid_gpa_error)
                toast(getString(R.string.invalid_gpa_toast))
                return@setOnClickListener
            }
            // Cập nhật sinh viên bằng hàm copy()
            currentStudent = currentStudent.copy(gpa = newGpa)
            bindStudentData(currentStudent) // Vẽ lại dữ liệu mới lên Views
            toast(getString(R.string.update_success_toast))
        }
    }

    private fun bindStudentData(student: Student) {
        with(binding) {
            tvName.text = student.name
            tvStudentId.text = getString(R.string.student_info_format, student.id, student.className)
            tvPhone.text = getString(R.string.phone_label, student.phone)
            tvGpaBadge.text = getString(R.string.gpa_badge_format, student.gpa, student.gpa.toAcademicRanking())
            edtNewGpa.setText(student.gpa.toString())
        }
    }
}
