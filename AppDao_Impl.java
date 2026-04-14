package com.example.employeeperformancetracker.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Employee> __insertionAdapterOfEmployee;

  private final EntityInsertionAdapter<Task> __insertionAdapterOfTask;

  private final EntityInsertionAdapter<Performance> __insertionAdapterOfPerformance;

  private final EntityInsertionAdapter<Attendance> __insertionAdapterOfAttendance;

  private final EntityDeletionOrUpdateAdapter<Employee> __deletionAdapterOfEmployee;

  private final EntityDeletionOrUpdateAdapter<Task> __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Performance> __deletionAdapterOfPerformance;

  private final EntityDeletionOrUpdateAdapter<Employee> __updateAdapterOfEmployee;

  private final EntityDeletionOrUpdateAdapter<Task> __updateAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Performance> __updateAdapterOfPerformance;

  private final EntityDeletionOrUpdateAdapter<Attendance> __updateAdapterOfAttendance;

  public AppDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmployee = new EntityInsertionAdapter<Employee>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `employees` (`id`,`name`,`role`,`department`,`joiningDate`,`email`,`contact`,`imageUrl`,`salary`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Employee entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getRole());
        statement.bindString(4, entity.getDepartment());
        statement.bindString(5, entity.getJoiningDate());
        statement.bindString(6, entity.getEmail());
        statement.bindString(7, entity.getContact());
        if (entity.getImageUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getImageUrl());
        }
        statement.bindDouble(9, entity.getSalary());
      }
    };
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tasks` (`id`,`employeeId`,`title`,`description`,`status`,`priority`,`deadline`,`completionDate`,`progress`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEmployeeId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getStatus());
        statement.bindString(6, entity.getPriority());
        statement.bindString(7, entity.getDeadline());
        if (entity.getCompletionDate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCompletionDate());
        }
        statement.bindLong(9, entity.getProgress());
      }
    };
    this.__insertionAdapterOfPerformance = new EntityInsertionAdapter<Performance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `performance` (`id`,`employeeId`,`period`,`date`,`reviewerName`,`qualityScore`,`timelinessScore`,`attendanceScore`,`communicationScore`,`innovationScore`,`overallRating`,`remarks`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Performance entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEmployeeId());
        statement.bindString(3, entity.getPeriod());
        statement.bindString(4, entity.getDate());
        statement.bindString(5, entity.getReviewerName());
        statement.bindLong(6, entity.getQualityScore());
        statement.bindLong(7, entity.getTimelinessScore());
        statement.bindLong(8, entity.getAttendanceScore());
        statement.bindLong(9, entity.getCommunicationScore());
        statement.bindLong(10, entity.getInnovationScore());
        statement.bindDouble(11, entity.getOverallRating());
        statement.bindString(12, entity.getRemarks());
      }
    };
    this.__insertionAdapterOfAttendance = new EntityInsertionAdapter<Attendance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `attendance` (`id`,`employeeId`,`date`,`status`,`checkIn`,`checkOut`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Attendance entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEmployeeId());
        statement.bindString(3, entity.getDate());
        statement.bindString(4, entity.getStatus());
        if (entity.getCheckIn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCheckIn());
        }
        if (entity.getCheckOut() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCheckOut());
        }
      }
    };
    this.__deletionAdapterOfEmployee = new EntityDeletionOrUpdateAdapter<Employee>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `employees` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Employee entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tasks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfPerformance = new EntityDeletionOrUpdateAdapter<Performance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `performance` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Performance entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfEmployee = new EntityDeletionOrUpdateAdapter<Employee>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `employees` SET `id` = ?,`name` = ?,`role` = ?,`department` = ?,`joiningDate` = ?,`email` = ?,`contact` = ?,`imageUrl` = ?,`salary` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Employee entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getRole());
        statement.bindString(4, entity.getDepartment());
        statement.bindString(5, entity.getJoiningDate());
        statement.bindString(6, entity.getEmail());
        statement.bindString(7, entity.getContact());
        if (entity.getImageUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getImageUrl());
        }
        statement.bindDouble(9, entity.getSalary());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `id` = ?,`employeeId` = ?,`title` = ?,`description` = ?,`status` = ?,`priority` = ?,`deadline` = ?,`completionDate` = ?,`progress` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEmployeeId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getStatus());
        statement.bindString(6, entity.getPriority());
        statement.bindString(7, entity.getDeadline());
        if (entity.getCompletionDate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCompletionDate());
        }
        statement.bindLong(9, entity.getProgress());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__updateAdapterOfPerformance = new EntityDeletionOrUpdateAdapter<Performance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `performance` SET `id` = ?,`employeeId` = ?,`period` = ?,`date` = ?,`reviewerName` = ?,`qualityScore` = ?,`timelinessScore` = ?,`attendanceScore` = ?,`communicationScore` = ?,`innovationScore` = ?,`overallRating` = ?,`remarks` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Performance entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEmployeeId());
        statement.bindString(3, entity.getPeriod());
        statement.bindString(4, entity.getDate());
        statement.bindString(5, entity.getReviewerName());
        statement.bindLong(6, entity.getQualityScore());
        statement.bindLong(7, entity.getTimelinessScore());
        statement.bindLong(8, entity.getAttendanceScore());
        statement.bindLong(9, entity.getCommunicationScore());
        statement.bindLong(10, entity.getInnovationScore());
        statement.bindDouble(11, entity.getOverallRating());
        statement.bindString(12, entity.getRemarks());
        statement.bindLong(13, entity.getId());
      }
    };
    this.__updateAdapterOfAttendance = new EntityDeletionOrUpdateAdapter<Attendance>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `attendance` SET `id` = ?,`employeeId` = ?,`date` = ?,`status` = ?,`checkIn` = ?,`checkOut` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Attendance entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getEmployeeId());
        statement.bindString(3, entity.getDate());
        statement.bindString(4, entity.getStatus());
        if (entity.getCheckIn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCheckIn());
        }
        if (entity.getCheckOut() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCheckOut());
        }
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insertEmployee(final Employee employee,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEmployee.insertAndReturnId(employee);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTask(final Task task, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTask.insertAndReturnId(task);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPerformance(final Performance performance,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPerformance.insertAndReturnId(performance);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAttendance(final Attendance attendance,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAttendance.insertAndReturnId(attendance);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEmployee(final Employee employee,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEmployee.handle(employee);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTask(final Task task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTask.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePerformance(final Performance performance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPerformance.handle(performance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEmployee(final Employee employee,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEmployee.handle(employee);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTask(final Task task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTask.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePerformance(final Performance performance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPerformance.handle(performance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAttendance(final Attendance attendance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAttendance.handle(attendance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Employee>> getAllEmployees() {
    final String _sql = "SELECT * FROM employees ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"employees"}, new Callable<List<Employee>>() {
      @Override
      @NonNull
      public List<Employee> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfContact = CursorUtil.getColumnIndexOrThrow(_cursor, "contact");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSalary = CursorUtil.getColumnIndexOrThrow(_cursor, "salary");
          final List<Employee> _result = new ArrayList<Employee>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Employee _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRole;
            _tmpRole = _cursor.getString(_cursorIndexOfRole);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpJoiningDate;
            _tmpJoiningDate = _cursor.getString(_cursorIndexOfJoiningDate);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpContact;
            _tmpContact = _cursor.getString(_cursorIndexOfContact);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final double _tmpSalary;
            _tmpSalary = _cursor.getDouble(_cursorIndexOfSalary);
            _item = new Employee(_tmpId,_tmpName,_tmpRole,_tmpDepartment,_tmpJoiningDate,_tmpEmail,_tmpContact,_tmpImageUrl,_tmpSalary);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getEmployeeById(final int empId, final Continuation<? super Employee> $completion) {
    final String _sql = "SELECT * FROM employees WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, empId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Employee>() {
      @Override
      @Nullable
      public Employee call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfContact = CursorUtil.getColumnIndexOrThrow(_cursor, "contact");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSalary = CursorUtil.getColumnIndexOrThrow(_cursor, "salary");
          final Employee _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRole;
            _tmpRole = _cursor.getString(_cursorIndexOfRole);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpJoiningDate;
            _tmpJoiningDate = _cursor.getString(_cursorIndexOfJoiningDate);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpContact;
            _tmpContact = _cursor.getString(_cursorIndexOfContact);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final double _tmpSalary;
            _tmpSalary = _cursor.getDouble(_cursorIndexOfSalary);
            _result = new Employee(_tmpId,_tmpName,_tmpRole,_tmpDepartment,_tmpJoiningDate,_tmpEmail,_tmpContact,_tmpImageUrl,_tmpSalary);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Task>> getAllTasks() {
    final String _sql = "SELECT * FROM tasks ORDER BY deadline ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCompletionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "completionDate");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getInt(_cursorIndexOfEmployeeId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpDeadline;
            _tmpDeadline = _cursor.getString(_cursorIndexOfDeadline);
            final String _tmpCompletionDate;
            if (_cursor.isNull(_cursorIndexOfCompletionDate)) {
              _tmpCompletionDate = null;
            } else {
              _tmpCompletionDate = _cursor.getString(_cursorIndexOfCompletionDate);
            }
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            _item = new Task(_tmpId,_tmpEmployeeId,_tmpTitle,_tmpDescription,_tmpStatus,_tmpPriority,_tmpDeadline,_tmpCompletionDate,_tmpProgress);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Task>> getTasksForEmployee(final int empId) {
    final String _sql = "SELECT * FROM tasks WHERE employeeId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, empId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDeadline = CursorUtil.getColumnIndexOrThrow(_cursor, "deadline");
          final int _cursorIndexOfCompletionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "completionDate");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getInt(_cursorIndexOfEmployeeId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpDeadline;
            _tmpDeadline = _cursor.getString(_cursorIndexOfDeadline);
            final String _tmpCompletionDate;
            if (_cursor.isNull(_cursorIndexOfCompletionDate)) {
              _tmpCompletionDate = null;
            } else {
              _tmpCompletionDate = _cursor.getString(_cursorIndexOfCompletionDate);
            }
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            _item = new Task(_tmpId,_tmpEmployeeId,_tmpTitle,_tmpDescription,_tmpStatus,_tmpPriority,_tmpDeadline,_tmpCompletionDate,_tmpProgress);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Performance>> getAllPerformances() {
    final String _sql = "SELECT * FROM performance ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"performance"}, new Callable<List<Performance>>() {
      @Override
      @NonNull
      public List<Performance> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "period");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfReviewerName = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewerName");
          final int _cursorIndexOfQualityScore = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityScore");
          final int _cursorIndexOfTimelinessScore = CursorUtil.getColumnIndexOrThrow(_cursor, "timelinessScore");
          final int _cursorIndexOfAttendanceScore = CursorUtil.getColumnIndexOrThrow(_cursor, "attendanceScore");
          final int _cursorIndexOfCommunicationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "communicationScore");
          final int _cursorIndexOfInnovationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "innovationScore");
          final int _cursorIndexOfOverallRating = CursorUtil.getColumnIndexOrThrow(_cursor, "overallRating");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final List<Performance> _result = new ArrayList<Performance>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Performance _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getInt(_cursorIndexOfEmployeeId);
            final String _tmpPeriod;
            _tmpPeriod = _cursor.getString(_cursorIndexOfPeriod);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpReviewerName;
            _tmpReviewerName = _cursor.getString(_cursorIndexOfReviewerName);
            final int _tmpQualityScore;
            _tmpQualityScore = _cursor.getInt(_cursorIndexOfQualityScore);
            final int _tmpTimelinessScore;
            _tmpTimelinessScore = _cursor.getInt(_cursorIndexOfTimelinessScore);
            final int _tmpAttendanceScore;
            _tmpAttendanceScore = _cursor.getInt(_cursorIndexOfAttendanceScore);
            final int _tmpCommunicationScore;
            _tmpCommunicationScore = _cursor.getInt(_cursorIndexOfCommunicationScore);
            final int _tmpInnovationScore;
            _tmpInnovationScore = _cursor.getInt(_cursorIndexOfInnovationScore);
            final float _tmpOverallRating;
            _tmpOverallRating = _cursor.getFloat(_cursorIndexOfOverallRating);
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            _item = new Performance(_tmpId,_tmpEmployeeId,_tmpPeriod,_tmpDate,_tmpReviewerName,_tmpQualityScore,_tmpTimelinessScore,_tmpAttendanceScore,_tmpCommunicationScore,_tmpInnovationScore,_tmpOverallRating,_tmpRemarks);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Performance>> getPerformanceForEmployee(final int empId) {
    final String _sql = "SELECT * FROM performance WHERE employeeId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, empId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"performance"}, new Callable<List<Performance>>() {
      @Override
      @NonNull
      public List<Performance> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfPeriod = CursorUtil.getColumnIndexOrThrow(_cursor, "period");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfReviewerName = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewerName");
          final int _cursorIndexOfQualityScore = CursorUtil.getColumnIndexOrThrow(_cursor, "qualityScore");
          final int _cursorIndexOfTimelinessScore = CursorUtil.getColumnIndexOrThrow(_cursor, "timelinessScore");
          final int _cursorIndexOfAttendanceScore = CursorUtil.getColumnIndexOrThrow(_cursor, "attendanceScore");
          final int _cursorIndexOfCommunicationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "communicationScore");
          final int _cursorIndexOfInnovationScore = CursorUtil.getColumnIndexOrThrow(_cursor, "innovationScore");
          final int _cursorIndexOfOverallRating = CursorUtil.getColumnIndexOrThrow(_cursor, "overallRating");
          final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
          final List<Performance> _result = new ArrayList<Performance>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Performance _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getInt(_cursorIndexOfEmployeeId);
            final String _tmpPeriod;
            _tmpPeriod = _cursor.getString(_cursorIndexOfPeriod);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpReviewerName;
            _tmpReviewerName = _cursor.getString(_cursorIndexOfReviewerName);
            final int _tmpQualityScore;
            _tmpQualityScore = _cursor.getInt(_cursorIndexOfQualityScore);
            final int _tmpTimelinessScore;
            _tmpTimelinessScore = _cursor.getInt(_cursorIndexOfTimelinessScore);
            final int _tmpAttendanceScore;
            _tmpAttendanceScore = _cursor.getInt(_cursorIndexOfAttendanceScore);
            final int _tmpCommunicationScore;
            _tmpCommunicationScore = _cursor.getInt(_cursorIndexOfCommunicationScore);
            final int _tmpInnovationScore;
            _tmpInnovationScore = _cursor.getInt(_cursorIndexOfInnovationScore);
            final float _tmpOverallRating;
            _tmpOverallRating = _cursor.getFloat(_cursorIndexOfOverallRating);
            final String _tmpRemarks;
            _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
            _item = new Performance(_tmpId,_tmpEmployeeId,_tmpPeriod,_tmpDate,_tmpReviewerName,_tmpQualityScore,_tmpTimelinessScore,_tmpAttendanceScore,_tmpCommunicationScore,_tmpInnovationScore,_tmpOverallRating,_tmpRemarks);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Attendance>> getAttendanceForEmployee(final int empId) {
    final String _sql = "SELECT * FROM attendance WHERE employeeId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, empId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"attendance"}, new Callable<List<Attendance>>() {
      @Override
      @NonNull
      public List<Attendance> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCheckIn = CursorUtil.getColumnIndexOrThrow(_cursor, "checkIn");
          final int _cursorIndexOfCheckOut = CursorUtil.getColumnIndexOrThrow(_cursor, "checkOut");
          final List<Attendance> _result = new ArrayList<Attendance>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Attendance _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getInt(_cursorIndexOfEmployeeId);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpCheckIn;
            if (_cursor.isNull(_cursorIndexOfCheckIn)) {
              _tmpCheckIn = null;
            } else {
              _tmpCheckIn = _cursor.getString(_cursorIndexOfCheckIn);
            }
            final String _tmpCheckOut;
            if (_cursor.isNull(_cursorIndexOfCheckOut)) {
              _tmpCheckOut = null;
            } else {
              _tmpCheckOut = _cursor.getString(_cursorIndexOfCheckOut);
            }
            _item = new Attendance(_tmpId,_tmpEmployeeId,_tmpDate,_tmpStatus,_tmpCheckIn,_tmpCheckOut);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Attendance>> getAttendanceByDate(final String date) {
    final String _sql = "SELECT * FROM attendance WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"attendance"}, new Callable<List<Attendance>>() {
      @Override
      @NonNull
      public List<Attendance> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCheckIn = CursorUtil.getColumnIndexOrThrow(_cursor, "checkIn");
          final int _cursorIndexOfCheckOut = CursorUtil.getColumnIndexOrThrow(_cursor, "checkOut");
          final List<Attendance> _result = new ArrayList<Attendance>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Attendance _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpEmployeeId;
            _tmpEmployeeId = _cursor.getInt(_cursorIndexOfEmployeeId);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpCheckIn;
            if (_cursor.isNull(_cursorIndexOfCheckIn)) {
              _tmpCheckIn = null;
            } else {
              _tmpCheckIn = _cursor.getString(_cursorIndexOfCheckIn);
            }
            final String _tmpCheckOut;
            if (_cursor.isNull(_cursorIndexOfCheckOut)) {
              _tmpCheckOut = null;
            } else {
              _tmpCheckOut = _cursor.getString(_cursorIndexOfCheckOut);
            }
            _item = new Attendance(_tmpId,_tmpEmployeeId,_tmpDate,_tmpStatus,_tmpCheckIn,_tmpCheckOut);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
